/**
 * Copyright (C), 科大讯飞股份有限公司
 * FileName: ImagerServiceImpl
 */
package com.iflytek.iaas.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.iflytek.iaas.config.ImageHubConfig;
import com.iflytek.iaas.consts.ReturnCode;
import com.iflytek.iaas.dao.ImageDao;
import com.iflytek.iaas.domain.Image;
import com.iflytek.iaas.dto.ImageDTO;
import com.iflytek.iaas.exception.BusiException;
import com.iflytek.iaas.http.HttpAPIService;
import com.iflytek.iaas.http.HttpResult;
import com.iflytek.iaas.service.ImageService;
import com.iflytek.iaas.utils.ShellHelper;
import com.iflytek.iaas.vo.ImageVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 〈集群服务〉
 *
 * @author ruizhao3
 * @create 2018/4/11
 */
@Service("ImageService")
public class ImageServiceImpl implements ImageService {

    private Logger logger = LoggerFactory.getLogger(ImageService.class);

    @Autowired
    private ImageDao imageDao;

    @Autowired
    private ImageHubConfig imageHubConfig;

    @Autowired
    private HttpAPIService httpAPIService;

    @Override
    public Page<ImageDTO> findByNameLike(String name, String version, Integer page, Integer pagesize) {

        Image image = new Image();
        if (!StringUtils.isEmpty(name)) {
            image.setName("%" + name + "%");
        }
        image.setVersion(version);
        image.setValid(true);

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains());

        //创建实例
        Example<Image> ex = Example.of(image, matcher);
        Page<Image> images = imageDao.findAll(ex, PageRequest.of(page - 1, pagesize, new Sort(Sort.Direction.DESC, "createtime")));

        Page<ImageDTO> dtos = images.map((model) -> {
            ImageDTO dto = new ImageDTO();
            BeanUtils.copyProperties(model, dto);
            return dto;
        });
        return dtos;
    }

    @Override
    public Boolean saveImage(ImageVO imageVO) {
        Image image = new Image();
        String hubPath = pushImage2Hub(imageVO.getFtpPath(), imageHubConfig);
        BeanUtils.copyProperties(imageVO, image);
        image.setHubPath(hubPath);
        Date time = new Date();
        image.setValid(true);
        image.setCreatetime(time);
        return imageDao.save(image) != null;
    }

    @Override
    public Boolean deleteImage(Integer imageId) {
        Image image = imageDao.getOne(imageId);
        //逻辑删除　并更新时间
        if (image != null && removeImageFromHub(image.getHubPath(), imageHubConfig)) {
            image.setValid(false);
            imageDao.saveAndFlush(image);
        }
        return true;
    }

    @Override
    public List<ImageDTO> searchImages(String name) {
        if (StringUtils.isEmpty(name)) {
            name = "%";
        }
        List<Image> images = imageDao.findByNameLikeAndValid("%" + name + "%", true);
        return images.stream().map((image) -> {
            ImageDTO dto = new ImageDTO();
            dto.setId(image.getId());
            dto.setName(image.getName());
            dto.setVersion(image.getVersion());
            dto.setValid(image.getValid());
            return dto;
        }).collect(Collectors.toList());
    }

    /**
     * push　镜像到docker私有仓库
     *
     * @param path
     * @param hubConfig
     * @return
     */
    private String pushImage2Hub(String path, ImageHubConfig hubConfig) {
        if (StringUtils.isEmpty(path)) {
            throw new BusiException(ReturnCode.IMAGE_NULL_FTPPATH);
        }

        int index = path.lastIndexOf("/");
        String parentPath = path.substring(0, index);
        String imageName = path.substring(index + 1);
        ShellHelper helper = new ShellHelper(hubConfig.getSsh_address(), hubConfig.getSsh_port(), hubConfig.getSsh_user(), hubConfig.getSsh_pwd(), false);

        try {
            //1.进入镜像目录
            String pwdPath = helper.execWithResult("cd " + parentPath + "&&pwd");
            logger.error("cd => ", pwdPath);
            if (!parentPath.equals(pwdPath)) {
                throw new BusiException(ReturnCode.IMAGE_NOTESISTS_FTPPATH);
            }
            //2.登录 docker hub
            String loginResult = helper.execWithResult("docker login " + hubConfig.getHub_address() + " -u " + hubConfig.getHub_user() + " -p " + hubConfig.getHub_pwd());
            logger.error("login docker hub info => {} ", loginResult);
            if (!loginResult.contains("Succeeded")) {
                throw new BusiException(ReturnCode.IMAGEHUB_FAILED_PUSH);
            }
            //3.load image
            String loadedInfo = helper.execWithResult("docker load -i " + imageName);
            logger.info("image load info:{}", loadedInfo);
            if (!loadedInfo.contains("Loaded")) {
                throw new BusiException(ReturnCode.IMAGE_FAILED_LOAD);
            }
            //4.tag image
            String image = loadedInfo.substring(loadedInfo.indexOf(":") + 1).trim();
            String newImage = hubConfig.getHub_address() + "/" + hubConfig.getHub_tag() + image.substring(image.lastIndexOf("/"));
            helper.execWithResult("docker tag " + image + " " + newImage);
            image = newImage;
            //5.push image to hub
            String pushInfo = helper.execWithResult("docker push " + image);
            logger.info("image push info:{}", pushInfo);
            if (pushInfo.contains("Pushed") || pushInfo.contains("Layer already exists")) {
                return image;
            } else if (pushInfo.contains("exists")) {
                throw new BusiException(ReturnCode.IMAGEHUB_EXISTS);
            } else {
                throw new BusiException(ReturnCode.IMAGEHUB_FAILED_PUSH);
            }

        } catch (BusiException e) {
            logger.error("pushImage2Hub failed, info => ", e);
            throw e;
        } catch (Exception e) {
            logger.error("pushImage2Hub failed, info => ", e);
            throw new BusiException(ReturnCode.IMAGEHUB_FAILED_PUSH);
        } finally {
            //6.logout hub
            try {
                helper.execWithResult("docker logout");
            } catch (Exception e) {
                //ignore
            }
        }

    }

    /**
     * 删除镜像文件
     *
     * @param image
     * @param hubConfig
     * @return
     */
    private boolean removeImageFromHub(String image, ImageHubConfig hubConfig) {
        if (StringUtils.isEmpty(image)) {
            return true;
        }

        ShellHelper helper = new ShellHelper(hubConfig.getSsh_address(), hubConfig.getSsh_port(), hubConfig.getSsh_user(), hubConfig.getSsh_pwd(), false);

        try {
            //1.删除本地镜像
            String rmiInfo = helper.execWithResult("docker rmi -f $(docker images -q " + image + ")");
            logger.info("rmi info => {}", rmiInfo);
            //2.删除hub镜像

            JSONObject obj = new JSONObject();
            Map<String, Object> loginForm = new HashMap<>();
            loginForm.put("principal", hubConfig.getHub_user());
            loginForm.put("password", hubConfig.getHub_pwd());
            //2.1 login harbor
            HttpResult loginRs = httpAPIService.doPost(hubConfig.getHub_api() + "/login", loginForm);
            logger.info("login harbor info => {}", loginRs);
            if (loginRs.getCode() != HttpResult.OK) {
                throw new BusiException(ReturnCode.IMAGE_FAILED_REMOVE);
            }
            //2.2 delete harbor repo tag
            Map<String, String> cookie = new HashMap<>();
            cookie.put("Cookie", loginRs.getHeaders().get("Set-Cookie"));
            String repo = image.substring(image.indexOf("/") + 1, image.indexOf(":")).trim();
            String tag = image.substring(image.indexOf(":") + 1).trim();
            HttpResult removeRs = httpAPIService.doDelete(hubConfig.getHub_api() + "/api/repositories/" + repo + "/tags/" + tag, cookie);
            logger.info("delete repo tag from harbor info => {}", loginRs);
            if (removeRs.getCode() != HttpResult.OK) {
                throw new BusiException(ReturnCode.IMAGE_FAILED_REMOVE);
            }
            //2.3 logout
            httpAPIService.doGetWithHeaders(hubConfig.getHub_api() + "/log_out", cookie);

            return true;

        } catch (BusiException e) {
            logger.error("remove Image failed, info => ", e);
            throw e;
        } catch (Exception e) {
            logger.error("remove Image failed, info => ", e);
            throw new BusiException(ReturnCode.IMAGE_FAILED_REMOVE);
        }

    }
}

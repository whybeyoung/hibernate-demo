/**
 * Copyright (C), 科大讯飞股份有限公司
 * FileName: ImageController
 * Author:   xwliu
 * Date:     2018/4/2 15:02
 * Description: 镜像相关接口
 */
package com.iflytek.iaas.controller;

import com.alibaba.fastjson.JSON;
import com.iflytek.iaas.consts.ReturnCode;
import com.iflytek.iaas.exception.BusiException;
import com.iflytek.iaas.exception.ControllerException;
import com.iflytek.iaas.service.ImageService;
import com.iflytek.iaas.vo.ImageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 〈镜像相关接口〉
 *
 * @author xwliu
 * @create 2018/4/2
 */
@Api(value = "Image-API", description = "镜像管理相关接口")
@RequestMapping(path="/api/v1")
@RestController
public class ImageController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(ImageController.class);

    @Autowired
    private ImageService imageService;

    /**
     *上传创建镜像
     * @param request
     * @param image 镜像信息
     * @return
     * @throws ControllerException
     */
    @ApiOperation(value = "createImage",notes = "创建镜像")
    @ApiImplicitParam(name = "image", value = "镜像ftp路径", required = true, dataType = "ImageVO")
    @PostMapping("/images")
    public String createImage(HttpServletRequest request, @RequestBody ImageVO image) throws ControllerException {

        if(StringUtils.isEmpty(image.getName())){
            throw new ControllerException(ReturnCode.IMAGE_NULL_NAME);
        }
        if(StringUtils.isEmpty(image.getVersion())){
            throw new ControllerException(ReturnCode.IMAGE_NULL_VERSION);
        }
        if(StringUtils.isEmpty(image.getFtpPath())){
            throw new ControllerException(ReturnCode.IMAGE_NULL_FTPPATH);
        }
        try {
            imageService.saveImage(image);
            return SUCCESS;
        } catch (BusiException e){
            throw e;
        } catch (Exception e) {
            logger.info("createImage error:", e);
            throw new ControllerException(ReturnCode.EXCEPTION);
        }
    }

    /**
     * 删除镜像
     * @param request
     * @param imageId 镜像id
     * @return
     */
    @ApiOperation(value = "deleteImage",notes = "删除镜像")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "imageId", value = "镜像id", paramType = "path", required = true, dataType = "Integer"),
    })
    @DeleteMapping("/images/{imageId}")
    public String deleteImage(HttpServletRequest request, @PathVariable("imageId") Integer imageId) throws ControllerException {
        try {
            imageService.deleteImage(imageId);
        } catch (Exception e) {
            throw new ControllerException(ReturnCode.EXCEPTION);
        }
        return SUCCESS;
    }

    @ApiOperation(value = "createImage",notes = "创建镜像")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "name", value = "镜像名", paramType = "query", required = false, dataType = "String"),
            @ApiImplicitParam(name = "version", value = "版本", paramType = "query", required = false, dataType = "String"),
            @ApiImplicitParam(name = "page", value = "页码", paramType = "query", required = false, dataType = "String"),
            @ApiImplicitParam(name = "pagesize", value = "页长", paramType = "query", required = false, dataType = "String"),
    })
    @GetMapping("/images")
    public String queryImages(HttpServletRequest request,
                              @RequestParam(required = false) String name,
                              @RequestParam(required = false) String version,
                              @RequestParam(required = false) Integer page,
                              @RequestParam(required = false) Integer pagesize){
        page = page == null || page <= 0 ? 1 : page;
        pagesize = pagesize == null || pagesize <= 0 ? 10: pagesize;
        return JSON.toJSONString(imageService.findByNameLike(name, version, page, pagesize));

    }
}

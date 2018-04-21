/**
 * Copyright (C), 科大讯飞股份有限公司
 * FileName: DeployServiceImpl
 */
package com.iflytek.iaas.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.iflytek.iaas.consts.*;
import com.iflytek.iaas.dao.*;
import com.iflytek.iaas.domain.ClusterLabel;
import com.iflytek.iaas.domain.DeployApp;
import com.iflytek.iaas.domain.Image;
import com.iflytek.iaas.domain.ImageDeploy;
import com.iflytek.iaas.dto.*;
import com.iflytek.iaas.dto.k8s.*;
import com.iflytek.iaas.exception.BusiException;
import com.iflytek.iaas.service.DeployService;
import com.iflytek.iaas.service.K8SService;
import com.iflytek.iaas.service.LogService;
import com.iflytek.iaas.utils.ToolUtils;
import com.iflytek.iaas.vo.DeployAppVO;
import com.iflytek.iaas.vo.ImageDeployVO;
import com.iflytek.iaas.vo.ServiceDeployVO;
import io.kubernetes.client.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 〈部署服务〉
 *
 * @author ruizhao3
 * @create 2018/4/14
 */
@Service("DeployService")
public class DeployServiceImpl implements DeployService {

    private Logger logger = LoggerFactory.getLogger(DeployService.class);

    @Autowired
    private DeployAppDao deployAppDao;

    @Autowired
    private K8SService k8SService;

    @Autowired
    private ImageDao imageDao;

    @Autowired
    private ImageDeployDao imageDeployDao;

    @Autowired
    private ClusterLabelDao clusterLabelDao;

    @Autowired
    private LogService logService;

    @Override
    public Integer saveDeployApp(DeployAppVO appVO) {

        if (!CollectionUtils.isEmpty(deployAppDao.findByName(appVO.getName()))) {
            throw new BusiException(ReturnCode.DEPOY_APP_EXISTS);
        }

        if(deployAppDao.countByNodePortAndValid(appVO.getNodePort(), true) > 0){
            throw new BusiException(ReturnCode.DEPOY_PORT_BINDING);
        }

        DeployApp app = new DeployApp();
        BeanUtils.copyProperties(appVO, app);
        app.setCreatetime(new Date());
        //默认有效　未上线
        app.setValid(true);
        app.setStatus(false);
        return deployAppDao.save(app).getId();
    }

    @Override
    public Page<DeployAppDTO> findPagedList(String name, String creator, Integer page, Integer pagesize) {

        DeployApp appEx = new DeployApp();
        if (!StringUtils.isEmpty(name)) {
            appEx.setName("%" + name + "%");
        }
        if (!StringUtils.isEmpty(creator)) {
            appEx.setCreator("%" + creator + "%");
        }
        appEx.setValid(true);

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("creator", ExampleMatcher.GenericPropertyMatchers.contains())
                .withIgnorePaths("status");

        //创建实例
        Example<DeployApp> ex = Example.of(appEx, matcher);

        Page<DeployApp> apps = deployAppDao.findAll(ex, PageRequest.of(page - 1, pagesize, new Sort(Sort.Direction.DESC, "createtime")));
        List<Integer> appids = apps.stream().map((app) -> {
            return app.getId();
        }).collect(Collectors.toList());

        List<ImageDeploy> imageDps = imageDeployDao.findAllByAppIdInAndValid(appids, true);

        //获取每个应用部署的镜像
        Map<Integer, List<ImageDeployDTO>> imageMap = imageDps.stream().map((deployImage) -> {
            ImageDeployDTO dto = new ImageDeployDTO();
            BeanUtils.copyProperties(deployImage, dto);
            return dto;
        }).collect(Collectors.groupingBy(ImageDeployDTO::getAppId));

        return apps.map((app) -> {
            DeployAppDTO dto = new DeployAppDTO();
            BeanUtils.copyProperties(app, dto);
            dto.setDeployImages(imageMap.get(dto.getId()));
            return dto;
        });
    }

    @Override
    public Integer deployImage(ImageDeployVO deployVO) throws IOException, ApiException {
        DeployApp app = deployAppDao.findById(deployVO.getAppId()).get();
        if (app == null || !app.getValid()) {
            throw new BusiException(ReturnCode.DEPOY_UNKNOWN_APP);
        }
        Image image = imageDao.findById(deployVO.getImageId()).get();
        if (image == null || !image.getValid()) {
            throw new BusiException(ReturnCode.DEPOY_UNKNOWN_IMAGE);
        }
        ClusterLabel clusterLabel = clusterLabelDao.findOneByClusterId(deployVO.getClusterId());

        if (clusterLabel == null || clusterLabel.getValid() == false) {
            throw new BusiException(ReturnCode.DEPOY_UNKNOWN_CLUSTER);
        }

        String deployName = image.getName() + "-" + image.getVersion();

        if(imageDeployDao.countByNameAndValid(deployName, true) > 0){
            throw new BusiException(ReturnCode.DEPOY_REPEAT_IMAGE);
        }
        //build deploye label
        LabelDTO deployLabel = new LabelDTO();
        deployLabel.setKey(app.getNamespace() + "-" + deployName);
        deployLabel.setValue(deployName);
        //build serverLabel
        LabelDTO serverLabel = new LabelDTO();
        serverLabel.setKey(clusterLabel.getKey());
        serverLabel.setValue(clusterLabel.getValue());

        //deploy image by K8s API
        DeployConfigDTO deployConf = buildDeployConfigDTO(app.getNamespace(), deployVO, image, serverLabel, deployLabel);
        String params = JSON.toJSONString(deployConf);
        logger.info("Image [{}] deploy config => {}", deployName, params);
        OperationLogDTO logDTO = new OperationLogDTO(LogType.NEW_DEPLOY, "新建镜像部署", deployName, params, deployVO.getCreator());
        logService.saveOperationLog(logDTO);
        boolean deployed = k8SService.createImageDeployment(deployConf);
        if (!deployed) {
            throw new BusiException(ReturnCode.DEPOY_FAILED_IMAGE);
        }
        //persist to DB
        ImageDeploy pstDeploy = persistImageDeloyInfo(deployVO, deployName, deployLabel);
        //callback deployInfo
        ImageDeployInfoDTO deployInfo = k8SService.getImageDeploymentInfo(app.getNamespace(), deployName);

        logger.info("Image [{}] deploy info => {} ", deployName, JSON.toJSONString(deployInfo));
        if (deployInfo.getAvailable()) {
            pstDeploy.setDeployStatus(DeployStatus.DONE.byteVal());
            imageDeployDao.save(pstDeploy);
        }
        return pstDeploy.getId();
    }

    /**
     * 保存镜像部署信息到数据库
     *
     * @param deployVO
     * @param deployName
     * @param deployLabel
     * @return
     */
    private ImageDeploy persistImageDeloyInfo(ImageDeployVO deployVO, String deployName, LabelDTO deployLabel) {
        ImageDeploy deploy = new ImageDeploy();
        BeanUtils.copyProperties(deployVO, deploy);
        //healthcheck config with json
        if (!StringUtils.isEmpty(deployVO.getHealthCheck())) {
            K8sHcDTO hcDTO = new K8sHcDTO(K8sHCType.ExecAction.name(), deployVO.getHealthCheck());
            deploy.setHealthCheck(JSON.toJSONString(hcDTO));
        }
        deploy.setName(deployName);
        deploy.setDeployLabel(JSON.toJSONString(deployLabel));
        deploy.setAutoDispatch(true);
        deploy.setValid(true);
        deploy.setCreatetime(new Date());
        deploy.setDeployStatus(DeployStatus.DEPLOYING.byteVal());
        return imageDeployDao.save(deploy);
    }


    /**
     * 构建k8s部署配置对象
     *
     * @param namespace
     * @param deployVO
     * @param image
     * @param serverLabel
     * @param deployLabel
     * @return DeployConfigDTO
     */
    private DeployConfigDTO buildDeployConfigDTO(String namespace, ImageDeployVO deployVO, Image image, LabelDTO serverLabel, LabelDTO deployLabel) {
        DeployConfigDTO deployConf = new DeployConfigDTO();
        deployConf.setNamespace(namespace);
        //envs
        if (!CollectionUtils.isEmpty(deployVO.getEnvs())) {
            deployConf.setEnvs(deployVO.getEnvs());
        }
        //healthcheck cmd
        if (!StringUtils.isEmpty(deployVO.getHealthCheck())) {
            deployConf.setHealthCheckExec(deployVO.getHealthCheck());
        }

        deployConf.setImgDeployName(image.getName() + "-" + image.getVersion());
        deployConf.setImgPath(image.getHubPath());
        deployConf.setInitCmd(deployVO.getInitCmd());

        //pod params
        deployConf.setPods(deployVO.getPods());
        deployConf.setPodContainers(1);
        deployConf.setContainerPort(deployVO.getContainerPort());

        //memory and cpu limits
        if (deployVO.getMemoryLimits() != null) {
            deployConf.setMemoryLimits(deployVO.getMemoryLimits());
        }

        if (deployVO.getCpuLimits() != null) {
            deployConf.setCpuLimits(deployVO.getCpuLimits());
        }

        deployConf.setUniqueDeploy(deployVO.isUniqueDeploy());
        deployConf.setTimeOut(deployVO.getTimeOut());

        //labels
        deployConf.setServerLabel(serverLabel);
        deployConf.setDeployLabel(deployLabel);

        //mount dirs
        if (!CollectionUtils.isEmpty(deployVO.getMountDirs())) {
            for (MountVolumeDTO volume : deployVO.getMountDirs()) {
                volume.setName("volume-" + ToolUtils.getUniqueId());
            }
            deployConf.setMountDirs(deployVO.getMountDirs());
        }
        return deployConf;
    }

    @Override
    public boolean deleteDeployedImage(Integer imageDeployId, String operator) {
        //获取镜像部署信息
        ImageDeploy deployedImage = imageDeployDao.findById(imageDeployId).get();
        LabelDTO deployLable = JSON.parseObject(deployedImage.getDeployLabel(), LabelDTO.class);
        int index = deployLable.getKey().indexOf(deployLable.getValue());
        try {
            boolean deleted = k8SService.deleteImageDeployment(deployLable.getKey().substring(0, index - 1), deployedImage.getName());
            OperationLogDTO logDTO = new OperationLogDTO(LogType.DELETE, "删除镜像部署", deployedImage.getName(), JSON.toJSONString(deployLable), operator);
            logService.saveOperationLog(logDTO);
            if (deleted) {
                deployedImage.setValid(false);
                imageDeployDao.saveAndFlush(deployedImage);
            }
            return deleted;
        } catch (Exception e) {
            logger.error("k8s delete ImageDeployment [{}] failed, info=>", deployedImage.getName(), e);
            throw new BusiException(ReturnCode.DEPOY_FAILED_DELETEIMAGE);
        }
    }

    @Override
    public boolean deployService(ServiceDeployVO deployVO) {
        DeployApp app = deployAppDao.findById(deployVO.getAppId()).get();

        if(app.getStatus()){
            throw new BusiException(ReturnCode.DEPOY_REPEAT_SERVICE);
        }

        List<ImageDeploy> imageDeploys = imageDeployDao.findAllByAppIdInAndValid(Arrays.asList(deployVO.getAppId()), true);

        List<String> imgDepNames = imageDeploys.stream().map(d -> d.getName()).collect(Collectors.toList());
        List<String> labels = imageDeploys.stream().map(d -> d.getDeployLabel()).collect(Collectors.toList());

        if (CollectionUtils.isEmpty(imgDepNames)) {
            throw new BusiException(ReturnCode.DEPOY_UNKNOWN_DEPLOYEDIMAGE);
        }

        List<LabelDTO> labelDTOS = new ArrayList<>(imageDeploys.size());

        for (String lableStr : labels) {
            labelDTOS.add(JSON.parseObject(lableStr, LabelDTO.class));
        }

        ServiceConfigDTO srvDeploy = new ServiceConfigDTO();
        srvDeploy.setImgDeployNames(imgDepNames);
        srvDeploy.setPodPort(app.getPodPort());
        srvDeploy.setNodePort(app.getNodePort());
        srvDeploy.setType(K8sAPPType.ordinalOf(app.getApptype()));
        srvDeploy.setServiceName(app.getName());
        srvDeploy.setDeployLabels(labelDTOS);
        try {
            ServiceDeployInfoDTO deployInfo = k8SService.createServiceDeployment(srvDeploy);
            //log
            OperationLogDTO logDTO = new OperationLogDTO(LogType.NEW_DEPLOY, "新建服务部署", app.getName(), JSON.toJSONString(srvDeploy), deployVO.getCreator());
            logService.saveOperationLog(logDTO);
            if(deployInfo == null){
                throw new BusiException();
            }
            app.setIp(deployInfo.getIp());
            app.setStatus(true);
            app.setNodePort(deployInfo.getNodePort());
            app.setPodPort(deployInfo.getPodPort());
            deployAppDao.saveAndFlush(app);
            return true;
        } catch (Exception e) {
            logger.error("k8s create ServiceDeployment [{}] failed, info=>", app.getName(), e);
            throw new BusiException(ReturnCode.DEPOY_FAILED_SEVICE);
        }
    }

    @Override
    public boolean deleteDeployedService(Integer appId, String operator) {
        DeployApp app = deployAppDao.findById(appId).get();
        try {
            boolean deleted = k8SService.deleteServiceDeployment(app.getNamespace(), app.getName());
            //log
            OperationLogDTO logDTO = new OperationLogDTO(LogType.OFFLINE, "服务下线", app.getName(), JSON.toJSONString(app), operator);
            logService.saveOperationLog(logDTO);
            if(deleted){
                app.setStatus(false);
                deployAppDao.saveAndFlush(app);
            }
            return true;
        } catch (Exception e) {
            logger.error("k8s delete ServiceDeployment [{}] failed, info=>", app.getName(), e);
            throw new BusiException(ReturnCode.DEPOY_FAILED_DELETESEVICE);
        }
    }

    @Override
    public Map<Integer, ImageDeployInfoDTO> getDeployedImagePodsInfo(List<Integer> imgDids) {
        List<ImageDeploy> deploys = imageDeployDao.findAllById(imgDids);
        if (CollectionUtils.isEmpty(deploys)) {
            return new HashMap<>();
        }
        return deploys.stream().filter(p -> p.getValid())
                .map(d -> {
                    LabelDTO label = JSON.parseObject(d.getDeployLabel(), LabelDTO.class);
                    String lableKey = label.getKey();
                    String name = d.getName();
                    String namespace = lableKey.substring(0, lableKey.indexOf(label.getValue()) - 1);
                    try {
                        ImageDeployInfoDTO dto = k8SService.getImageDeploymentInfo(namespace, name);
                        return new ImageDeployPodsDTO(d.getId(), dto);
                    } catch (Exception e) {
                        //ignore
                    }
                    return null;
                })
                .filter(pod -> pod != null)
                .collect(Collectors.toMap(ImageDeployPodsDTO::getImageDid, ImageDeployPodsDTO::getDeployInfo));

    }

    @Override
    public List<ServerInfoDTO> getDeployedImageServers(Integer deployId) {
        ImageDeploy deployedImage = imageDeployDao.findById(deployId).get();

        LabelDTO deployLabel = JSON.parseObject(deployedImage.getDeployLabel(), LabelDTO.class);
        int index = deployLabel.getKey().indexOf(deployLabel.getValue());
        try {
            return k8SService.getServerNodesByDeployLabel(deployLabel.getKey().substring(0, index -1), deployLabel);
        } catch (Exception e) {
            logger.error("k8s get ServerNodes By DeployLabel failed, error=> {} ", deployId,  e);
        }
        return new ArrayList<>();
    }

    @Override
    public boolean scaleDeployedImagePods(Integer deployId, Integer pods, String operator) {
        if(pods == null|| pods < 0){
            throw new BusiException(ReturnCode.DEPOY_SCALE_ILLEGALPODS);
        }
        ImageDeploy deployedImage = imageDeployDao.findById(deployId).get();

        if(pods.compareTo(deployedImage.getMinPods()) < 0 || pods.compareTo(deployedImage.getMaxPods()) > 0){
            throw new BusiException(ReturnCode.DEPOY_SCALE_ILLEGALPODS);
        }

        LabelDTO deployLabel = JSON.parseObject(deployedImage.getDeployLabel(), LabelDTO.class);
        int index = deployLabel.getKey().indexOf(deployLabel.getValue());
        try {
            String ns = deployLabel.getKey().substring(0, index -1);
            boolean scaled = k8SService.updateDeployPodsByName(ns, deployedImage.getName(), pods);
            //log
            JSONObject params = new JSONObject();
            params.put("namespace", ns);
            params.put("deployname",deployedImage.getName());
            params.put("pods", pods);
            OperationLogDTO logDTO = new OperationLogDTO(LogType.SCALE, "伸缩", deployedImage.getName(), params.toJSONString(), operator);
            logService.saveOperationLog(logDTO);
            if(scaled){
                deployedImage.setPods(pods);
                imageDeployDao.saveAndFlush(deployedImage);
            }
            return scaled;
        } catch (Exception e) {
            logger.error("k8s update Deploy Pods failed, info=>[id={}, pods={}] ", deployId, pods, e);
            throw new BusiException(ReturnCode.DEPOY_SCALE_FAILED);
        }
    }
}

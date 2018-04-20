/**
 * Copyright (C), 科大讯飞股份有限公司
 * FileName: DeployController
 * Author:   xwliu
 * Date:     2018/4/2 15:03
 * Description: 镜像部署相关接口
 */
package com.iflytek.iaas.controller;

import com.alibaba.fastjson.JSON;
import com.iflytek.iaas.consts.ReturnCode;
import com.iflytek.iaas.domain.ImageDeploy;
import com.iflytek.iaas.dto.k8s.ImageDeployInfoDTO;
import com.iflytek.iaas.exception.BusiException;
import com.iflytek.iaas.exception.ControllerException;
import com.iflytek.iaas.service.DeployService;
import com.iflytek.iaas.service.K8SService;
import com.iflytek.iaas.utils.RegularUtils;
import com.iflytek.iaas.vo.DeployAppVO;
import com.iflytek.iaas.vo.ImageDeployVO;
import com.iflytek.iaas.vo.ServiceDeployVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import jdk.nashorn.internal.objects.annotations.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 〈镜像部署相关接口〉
 *
 * @author xwliu
 * @create 2018/4/2
 */
@Api(value = "DEPLOY-API", description = "部署相关接口")
@RequestMapping(path = "/api/v1/deploy")
@RestController
public class DeployController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(DeployController.class);

    @Autowired
    private DeployService deployService;

    @Autowired
    private K8SService k8SService;

    /**
     * 从k8s获取命名空间
     *
     * @param request
     * @return
     * @throws Exception
     */
    @ApiOperation(value = "namespaces", notes = "获取k8s命名空间")
    @GetMapping("/namespaces")
    public String queryImages(HttpServletRequest request) throws Exception {
        return JSON.toJSONString(k8SService.getNamespaces());

    }

    @ApiOperation(value = "createDeployApp", notes = "创建部署应用")
    @ApiImplicitParam(name = "apps", value = "应用信息", required = true, dataType = "DeployAppVO")
    @PostMapping("/apps")
    public Integer createDeployApp(HttpServletRequest request, @RequestBody DeployAppVO app) throws ControllerException {

        if (StringUtils.isEmpty(app.getName()) || !RegularUtils.isK8sAllowedName(app.getName())) {
            throw new ControllerException(ReturnCode.DEPOY_APP_ILLEGALNAME);
        }
        if (StringUtils.isEmpty(app.getNamespace())) {
            throw new ControllerException(ReturnCode.DEPOY_NS_NULLNAME);
        }
        if (app.getNodePort() != null && (app.getNodePort() < 30000 || app.getNodePort() > 32767)) {
            throw new ControllerException(ReturnCode.DEPOY_APP_ILLEGALPORT);
        }
        if (app.getPodPort() != null && (app.getPodPort() < 30000 || app.getPodPort() > 32767)) {
            throw new ControllerException(ReturnCode.DEPOY_APP_ILLEGALPORT);
        }
        app.setCreator(getCurrentUser(request).getNickname());
        try {
            return deployService.saveDeployApp(app);
        } catch (BusiException e) {
            throw e;
        } catch (Exception e) {
            logger.info("createDeployApp error:", e);
            throw new ControllerException(ReturnCode.EXCEPTION);
        }
    }

    @ApiOperation(value = "applist", notes = "应用列表")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "name", value = "应用名称", paramType = "query", required = false, dataType = "String"),
            @ApiImplicitParam(name = "creator", value = "创建人", paramType = "query", required = false, dataType = "String"),
            @ApiImplicitParam(name = "imagename", value = "镜像名称", paramType = "query", required = false, dataType = "String"),
            @ApiImplicitParam(name = "page", value = "页码", paramType = "query", required = false, dataType = "Integer"),
            @ApiImplicitParam(name = "pagesize", value = "页长", paramType = "query", required = false, dataType = "Integer"),
    })
    @GetMapping("/apps")
    public String queryImages(HttpServletRequest request,
                              @RequestParam(required = false) String name,
                              @RequestParam(required = false) String creator,
                              @RequestParam(required = false) String imagename,
                              @RequestParam(required = false) Integer page,
                              @RequestParam(required = false) Integer pagesize) {
        page = page == null || page <= 0 ? 1 : page;
        pagesize = pagesize == null || pagesize <= 0 ? 10 : pagesize;
        return JSON.toJSONString(deployService.findPagedList(name, creator, page, pagesize));

    }

    @ApiOperation(value = "deployImage", notes = "部署镜像")
    @ApiImplicitParam(name = "deployVO", value = "镜像部署信息", required = true, dataType = "ImageDeployVO")
    @PostMapping("/image")
    public String deployImage(HttpServletRequest request, @RequestBody ImageDeployVO deployVO) throws ControllerException {

        if (deployVO.getAppId() == null) {
            throw new ControllerException(ReturnCode.DEPOY_UNKNOWN_APP);
        }
        if (deployVO.getClusterId() == null) {
            throw new ControllerException(ReturnCode.DEPOY_UNKNOWN_CLUSTER);
        }
        if (deployVO.getImageId() == null) {
            throw new ControllerException(ReturnCode.DEPOY_UNKNOWN_IMAGE);
        }
        if (deployVO.getPods() == null || deployVO.getPods() < 1) {
            throw new ControllerException(ReturnCode.DEPOY_UNVALID_PODS);
        }
        if (deployVO.getMinPods() == null || deployVO.getMaxPods() == null || deployVO.getMinPods().compareTo(deployVO.getMaxPods()) > 0) {
            throw new ControllerException(ReturnCode.DEPOY_UNVALID_PODSLIMIT);
        }
        try {
            deployService.deployImage(deployVO);
            return SUCCESS;
        } catch (BusiException e) {
            throw e;
        } catch (Exception e) {
            logger.info("deploy Image error:", e);
            throw new ControllerException(ReturnCode.EXCEPTION);
        }
    }

    @ApiOperation(value = "deployService", notes = "部署镜像")
    @ApiImplicitParam(name = "deployVO", value = "镜像部署信息", required = true, dataType = "ServiceDeployVO")
    @PostMapping("/service")
    public String deployService(HttpServletRequest request, @RequestBody ServiceDeployVO deployVO) throws ControllerException {

        if (deployVO.getAppId() == null) {
            throw new ControllerException(ReturnCode.DEPOY_UNKNOWN_APP);
        }
        if (CollectionUtils.isEmpty(deployVO.getImageDids())) {
            throw new ControllerException(ReturnCode.DEPOY_UNKNOWN_DEPLOYEDIMAGE);
        }

        try {
            deployService.deployService(deployVO);
            return SUCCESS;
        } catch (BusiException e) {
            throw e;
        } catch (Exception e) {
            logger.info("deploy Service error:", e);
            throw new ControllerException(ReturnCode.EXCEPTION);
        }
    }

    /**
     * 删除镜像部署
     *
     * @param request
     * @param deployId 镜像部署id
     * @return
     */
    @ApiOperation(value = "deleteDeployedImage", notes = "删除镜像部署")
    @ApiImplicitParam(name = "deployId", value = "镜像部署id", paramType = "path", required = true, dataType = "Integer")
    @DeleteMapping("/images/{deployId}")
    public String deleteDeployedImage(HttpServletRequest request, @PathVariable("deployId") Integer deployId) throws ControllerException {
        try {
            deployService.deleteDeployedImage(deployId);
        } catch (BusiException e) {
            throw e;
        } catch (Exception e) {
            throw new ControllerException(ReturnCode.EXCEPTION);
        }
        return SUCCESS;
    }

    @ApiOperation(value = "deleteDeployedService", notes = "删除部署服务")
    @ApiImplicitParam(name = "appId", value = "服务部署id", paramType = "path", required = true, dataType = "Integer")
    @DeleteMapping("/service/{appId}")
    public String deleteDeployedService(HttpServletRequest request, @PathVariable("appId") Integer appId) throws ControllerException {
        try {
            deployService.deleteDeployedService(appId);
        } catch (BusiException e) {
            throw e;
        } catch (Exception e) {
            throw new ControllerException(ReturnCode.EXCEPTION);
        }
        return SUCCESS;
    }

    @ApiOperation(value = "getDeployedImagePods", notes = "获取镜像部署的节点数")
    @ApiImplicitParam(name = "ids", value = "镜像部署id集合", paramType = "query", required = true, dataType = "String", example = "1,2,3")
    @GetMapping("/image/pods")
    public Map<Integer, ImageDeployInfoDTO> getDeployedImagePods(HttpServletRequest request, @RequestParam(required = false) List<Integer> ids) {
        if (CollectionUtils.isEmpty(ids)) {
            return new HashMap<>();
        }
        return deployService.getDeployedImagePodsInfo(ids);

    }

    @ApiOperation(value = "scaleDeployedImagePods", notes = "伸缩镜像部署")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "deployId", value = "镜像部署id集合", paramType = "path", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "pods", value = "伸缩pods数", paramType = "query", required = true, dataType = "Integer"),
    })
    @PutMapping("/image/{deployId}/pods")
    public String scale(HttpServletRequest request,
                        @PathVariable("deployId") Integer deployId,
                        @RequestParam(value = "pods") Integer pods) {
        deployService.scaleDeployedImagePods(deployId, pods);
        return SUCCESS;
    }

    @ApiOperation(value = "getDeployedImageServers", notes = "获取部署镜像的服务器")
    @ApiImplicitParam(name = "deployId", value = "镜像部署id", paramType = "path", required = true, dataType = "Integer")
    @GetMapping("/image/{deployId}/servers")
    public String getDeployedImageServers(HttpServletRequest request, @PathVariable("deployId") Integer deployId) {
        return JSON.toJSONString(deployService.getDeployedImageServers(deployId));
    }

}

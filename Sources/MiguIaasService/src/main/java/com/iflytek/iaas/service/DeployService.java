/**
 * Copyright (C), 科大讯飞股份有限公司
 * FileName: DeployService
 * Author:   xwliu
 * Date:     2018/4/2 15:10
 * Description: 镜像部署服务
 */
package com.iflytek.iaas.service;


import com.iflytek.iaas.dto.DeployAppDTO;
import com.iflytek.iaas.dto.k8s.ImageDeployInfoDTO;
import com.iflytek.iaas.dto.k8s.ServerInfoDTO;
import com.iflytek.iaas.vo.DeployAppVO;
import com.iflytek.iaas.vo.ImageDeployVO;
import com.iflytek.iaas.vo.ServiceDeployVO;
import io.kubernetes.client.ApiException;
import org.springframework.data.domain.Page;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 〈镜像部署服务接口〉
 *
 * @author ruizhao3
 * @create 2018/4/16
 */
public interface DeployService {

    /**
     * 保存部署应用信息
     * @param appVO
     * @return  ID
     */
    Integer saveDeployApp(DeployAppVO appVO);

    /**
     * 查询应用列表
     * @param name
     * @param creator
     * @param page
     * @param pagesize
     * @return
     */
    Page<DeployAppDTO> findPagedList(String name, String creator, Integer page, Integer pagesize);

    /**
     * 镜像部署
     * @param deployVO
     * @return
     */
    Integer deployImage(ImageDeployVO deployVO) throws IOException, ApiException;

    /**
     * 删除部署镜像
     * @param imageDeployId　镜像部署id
     * @return
     */
    boolean deleteDeployedImage(Integer imageDeployId, String operator);

    /**
     * 部署服务
     * @param deployVO
     * @return
     */
    boolean deployService(ServiceDeployVO deployVO);

    /**
     * 删除已部署的服务
     * @param appId 应用id
     * @return
     */
    boolean deleteDeployedService(Integer appId, String operator);

    /**
     *获取已部署镜像的机器数
     * @param imgDids
     * @return
     */
    Map<Integer, ImageDeployInfoDTO> getDeployedImagePodsInfo(List<Integer> imgDids);

    /**
     * 获取部署镜像的部署主机信息
     * @param deployId　部署id
     * @return
     */
    List<ServerInfoDTO> getDeployedImageServers(Integer deployId);

    /**
     * 伸缩已部署镜像的pods
     * @param deployId 部署id
     * @param pods 伸缩到的pods
     * @param operator
     * @return
     */
    boolean scaleDeployedImagePods(Integer deployId, Integer pods, String operator);
}

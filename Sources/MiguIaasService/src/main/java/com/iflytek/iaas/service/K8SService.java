/**
 * Copyright (C), 科大讯飞股份有限公司
 * FileName: K8SService
 * Author:   xwliu
 * Date:     2018/4/2 15:11
 * Description: k8s服务
 */
package com.iflytek.iaas.service;


import com.iflytek.iaas.dto.k8s.DeployConfigDTO;
import com.iflytek.iaas.dto.k8s.ServerInfoDTO;
import com.iflytek.iaas.dto.k8s.ServerResourceDTO;

import java.util.Date;
import java.util.List;

/**
 * 〈k8s服务〉
 *
 * @author xwliu
 * @create 2018/4/2
 */
public interface K8SService {

    /**
     * 创建部署命名空间
     * @param namespace 命名空间字符串
     * @return
     */
    boolean createDeployNamespace(String namespace);

    /**
     * 新建部署
     * @param deployConfigDTO 部署参数配置
     * @return
     */
    boolean createDeploy(DeployConfigDTO deployConfigDTO);

    /**
     * 根据部署名称更新pod数量（扩容/缩容）
     * @param deployName 部署名称
     * @param pods pod实例数
     * @return
     */
    boolean updateDeployPodsByName(String deployName,int pods);


    /**
     * 获取k8s集群中所有可用主机节点信息
     * @return
     */
    List<ServerInfoDTO> getOnlineServerNodes();

    /**
     * 更新主机信息
     * @param nodeName 节点name
     * @param label 节点标签
     * @param status 节点状态
     * @return
     */
    boolean updateServerNode(String nodeName,String label,String status);


    /**
     * 根据主机节点name以及时间段获取主机资源使用情况
     * @param serverNames 主机节点name列表
     * @param beginTime 开始时间
     * @param endTime  结束时间
     * @return
     */
    List<ServerResourceDTO> getServerResourceByServerName(List<String> serverNames, Date beginTime, Date endTime);

}

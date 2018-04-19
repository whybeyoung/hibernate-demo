/**
 * Copyright (C), 科大讯飞股份有限公司
 * FileName: K8SService
 * Author:   xwliu
 * Date:     2018/4/2 15:11
 * Description: k8s服务
 */
package com.iflytek.iaas.service;


import com.alibaba.fastjson.JSONArray;
import com.iflytek.iaas.dto.k8s.*;
import io.kubernetes.client.ApiException;

import java.io.IOException;
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
    Boolean createDeployNamespace(String namespace)throws IOException, ApiException;

    /**
     * 删除命名空间
     * @param namespace 命名空间
     * @return
     * @throws IOException
     * @throws ApiException
     */
    Boolean deleteNamespace(String namespace)throws IOException, ApiException;

    /**
     *  获取k8s集群中命名空间列表
     * @return
     * @throws IOException
     * @throws ApiException
     */
    List<String> getNamespaces() throws IOException, ApiException;;

    /**
     * 新建镜像部署
     * @param deployConfigDTO 部署参数配置
     * @return
     * @throws IOException
     * @throws ApiException
     */
    Boolean createImageDeployment(DeployConfigDTO deployConfigDTO)throws IOException, ApiException;

    /**
     * 删除镜像部署
     * @param namespace 命名空间
     * @param name 镜像部署名
     * @return
     * @throws IOException
     * @throws ApiException
     */
    Boolean deleteImageDeployment(String namespace,String name)throws IOException, ApiException;

    /**
     * 获取镜像部署详情
     * @param namespace
     * @param name
     * @return
     * @throws IOException
     * @throws ApiException
     */
    ImageDeployInfoDTO getImageDeploymentInfo(String namespace,String name)throws IOException, ApiException;
    /**
     * 新建服务部署
     * @return
     * @throws IOException
     * @throws ApiException
     */
    ServiceDeployInfoDTO createServiceDeployment(ServiceConfigDTO serviceConfigDTO)throws IOException, ApiException;

    /**
     * 删除服务部署
     * @param namespace 命名空间
     * @param name 服务部署名
     * @return
     * @throws IOException
     * @throws ApiException
     */
    Boolean deleteServiceDeployment(String namespace,String name)throws IOException, ApiException;

    /**
     * 获取服务部署详情
     * @param namespace 命名空间
     * @param name 服务部署名
     * @return
     * @throws IOException
     * @throws ApiException
     */
    ServiceDeployInfoDTO getServiceDeploymentInfo(String namespace,String name)throws IOException, ApiException;

    /**
     * 根据部署名称更新pod数量（扩容/缩容）
     * @param namespace 命名空间
     * @param deployName 部署名称
     * @param pods pod实例数
     * @return
     * @throws IOException
     * @throws ApiException
     */
    Boolean updateDeployPodsByName(String namespace,String deployName,int pods)throws IOException, ApiException;

    /**
     * 获取k8s集群中所有可用主机节点信息
     * @return
     */
    List<ServerInfoDTO> getAllServerNodes()throws IOException, ApiException;

    /**
     * 根据主机名获取主机信息
     * @param hostname 主机名
     * @return
     * @throws IOException
     * @throws ApiException
     */
    ServerInfoDTO getServerInfoByHostname(String hostname) throws IOException, ApiException;

    /**
     * 根据主机标签获取主机列表
     * @param serverLabel 主机标签
     * @return
     * @throws IOException
     * @throws ApiException
     */
    List<ServerInfoDTO> getServerNodesByServerLabel(LabelDTO serverLabel)throws IOException, ApiException;

    /**
     * 根据命名空间和部署标签获取主机列表
     * @param namespace
     * @param deploylabel
     * @return
     * @throws IOException
     * @throws ApiException
     */
    List<ServerInfoDTO> getServerNodesByDeployLabel(String namespace,LabelDTO deployLabel)throws IOException, ApiException;


    /**
     * 新增主机标签
     * @param hostName 主机名
     * @param labels 标签列表
     * @return
     * @throws IOException
     * @throws ApiException
     */
    Boolean createServerLabel(String hostName, List<LabelDTO> labels)throws IOException, ApiException;

    /**
     * 删除主机标签
     * @param hostName 主机名
     * @param labels 标签列表
     * @return
     * @throws IOException
     * @throws ApiException
     */
    Boolean deleteServerLabel(String hostName,List<LabelDTO> labels) throws IOException, ApiException;

    /**
     * 获取服务器CPU平均使用率，如果只传一个hostname,获取的就是单台服务器cpu使用率，多个hostname,获取的就是多个服务器平均的cpu使用率
     * @param hostNames 服务器的hostname
     * @param start 开始时间，linux时间戳
     * @param end 结束时间，linux时间戳
     * @param step 步长，单位是s，按多少秒取数据点，默认是60s
     * @return 返回json字符串
     */
    JSONArray getServerCPUUsageRateByHostname(List<String> hostNames,long start,long end,int step);

    /**
     * 获取服务器内存平均使用率，如果只传一个hostname,获取的就是单台服务器内存使用率，多个hostname,获取的就是多个服务器平均的内存使用率
     * @param hostNames 服务器的hostname
     * @param start 开始时间，linux时间戳
     * @param end 结束时间，linux时间戳
     * @param step 步长，单位是s，按多少秒取数据点，默认是60s
     * @return 返回json字符串
     */
    JSONArray getServerMemoryUsageRateByHostname(List<String> hostNames, long start, long end, int step);

    /**
     * 获取服务器网络平均使用率，如果只传一个hostname,获取的就是单台服务器内存使用率，多个hostname,获取的就是多个服务器平均的内存使用率
     * @param hostNames 服务器的hostname
     * @param start 开始时间，linux时间戳
     * @param end 结束时间，linux时间戳
     * @param step 步长，单位是s，按多少秒取数据点，默认是60s
     * @return 返回json字符串
     */
    NetworkFlowDTO getServerNetworkUsageRateByHostname(List<String> hostNames, long start, long end, int step);

    /**
     * 获取集群中部署在主机hostname上，并且有标签label的pod列表
     * @param label 集群标签
     * @param hostnames 集群中的主机名列表
     * @return
     * @throws IOException
     * @throws ApiException
     */
    List<PodDTO> getPodsByCluster(LabelDTO label,List<String> hostnames) throws IOException, ApiException;

    /**
     * 根据服务器hostname获取服务器硬盘信息
     * @param hostName 服务器hostname
     * @return
     */
    String getServerDiskByHostname(String hostName);

}

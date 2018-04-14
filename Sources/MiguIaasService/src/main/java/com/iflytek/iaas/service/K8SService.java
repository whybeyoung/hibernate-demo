/**
 * Copyright (C), 科大讯飞股份有限公司
 * FileName: K8SService
 * Author:   xwliu
 * Date:     2018/4/2 15:11
 * Description: k8s服务
 */
package com.iflytek.iaas.service;


import com.iflytek.iaas.dto.k8s.*;
import io.kubernetes.client.ApiException;

import java.io.IOException;
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
    boolean createDeployNamespace(String namespace)throws IOException, ApiException;

    /**
     * 删除命名空间
     * @param ns 命名空间
     * @return
     * @throws IOException
     * @throws ApiException
     */
    boolean deleteNamespace(NamespaceDTO ns)throws IOException, ApiException;

    /**
     *  获取k8s集群中命名空间列表
     * @return
     * @throws IOException
     * @throws ApiException
     */
    List<NamespaceDTO> getNamespaces() throws IOException, ApiException;;

    /**
     * 新建镜像部署
     * @param deployConfigDTO 部署参数配置
     * @return
     * @throws IOException
     * @throws ApiException
     */
    boolean createImageDeployment(DeployConfigDTO deployConfigDTO)throws IOException, ApiException;

    /**
     * 删除镜像部署
     * @param namespace 命名空间
     * @param name 镜像部署名
     * @return
     * @throws IOException
     * @throws ApiException
     */
    boolean deleteImageDeployment(String namespace,String name)throws IOException, ApiException;

    /**
     * 获取镜像部署详情
     * @param namespace
     * @param name
     * @return
     * @throws IOException
     * @throws ApiException
     */
    DeployInfoDTO getImageDeploymentInfo(String namespace,String name)throws IOException, ApiException;
    /**
     * 新建服务部署
     * @return
     * @throws IOException
     * @throws ApiException
     */
    boolean createServiceDeployment(ServiceConfigDTO serviceConfigDTO)throws IOException, ApiException;

    /**
     * 删除服务部署
     * @param namespace 命名空间
     * @param name 服务部署名
     * @return
     * @throws IOException
     * @throws ApiException
     */
    boolean deleteServiceDeployment(String namespace,String name)throws IOException, ApiException;

    /**
     * 根据部署名称更新pod数量（扩容/缩容）
     * @param namespace 命名空间
     * @param deployName 部署名称
     * @param pods pod实例数
     * @return
     * @throws IOException
     * @throws ApiException
     */
    boolean updateDeployPodsByName(String namespace,String deployName,int pods)throws IOException, ApiException;


    /**
     * 获取k8s集群中所有可用主机节点信息
     * @return
     */
    List<ServerInfoDTO> getOnlineServerNodes()throws IOException, ApiException;

    /**
     * 根据主机名获取主机信息
     * @param hostName 主机名
     * @return
     * @throws IOException
     * @throws ApiException
     */
    ServerInfoDTO getServerInfoByName(String hostName) throws IOException, ApiException;


    /**
     * 新增主机标签
     * @param hostName 主机名
     * @param labels 标签列表
     * @return
     * @throws IOException
     * @throws ApiException
     */
    boolean createServerLabel(String hostName, List<LabelDTO> labels)throws IOException, ApiException;

    /**
     * 删除主机标签
     * @param hostName 主机名
     * @param labels 标签列表
     * @return
     * @throws IOException
     * @throws ApiException
     */
    boolean deleteServerLabel(String hostName,List<LabelDTO> labels) throws IOException, ApiException;

    /**
     * 获取服务器CPU平均使用率，如果只传一个hostname,获取的就是单台服务器cpu使用率，多个hostname,获取的就是多个服务器平均的cpu使用率
     * @param hostNames 服务器的hostname
     * @param start 开始时间，linux时间戳
     * @param end 结束时间，linux时间戳
     * @param step 步长，单位是s，按多少秒取数据点，默认是60s
     * @return 返回json字符串
     */
    String getServerCPUUsageRateByHostname(List<String> hostNames,long start,long end,int step);

    /**
     * 获取服务器内存平均使用率，如果只传一个hostname,获取的就是单台服务器内存使用率，多个hostname,获取的就是多个服务器平均的内存使用率
     * @param hostNames 服务器的hostname
     * @param start 开始时间，linux时间戳
     * @param end 结束时间，linux时间戳
     * @param step 步长，单位是s，按多少秒取数据点，默认是60s
     * @return 返回json字符串
     */
    String getServerMemoryUsageRateByHostname(List<String> hostNames,long start,long end,int step);

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
     * 根据服务器hostname获取服务器硬盘信息
     * @param hostName 服务器hostname
     * @return
     */
    String getServerDiskByHostname(String hostName);

}

/**
 * Copyright (C), 科大讯飞股份有限公司
 * FileName: ClusterService
 * Author:   xwliu
 * Date:     2018/4/2 15:08
 * Description: 集群服务
 */
package com.iflytek.iaas.service;


import com.iflytek.iaas.dto.ClusterDTO;
import org.springframework.data.domain.Page;

/**
 * 〈集群服务接口〉
 *
 * @author xwliu
 * @create 2018/4/2
 */
public interface ClusterService {


    Page<ClusterDTO> findByNameLike(String name, Integer page, Integer pagesize);


    public Boolean saveClusterInfo(ClusterDTO clusterDTO);


    Integer getClusterSrvCount(Integer clusterId);
}

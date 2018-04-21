/**
 * Copyright (C), 科大讯飞股份有限公司
 * FileName: ClusterService
 * Author:   xwliu
 * Date:     2018/4/2 15:08
 * Description: 集群服务
 */
package com.iflytek.iaas.service;


import com.iflytek.iaas.domain.Cluster;
import com.iflytek.iaas.domain.User;
import com.iflytek.iaas.dto.ClusterDTO;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * 〈集群服务接口〉
 *
 * @author xwliu
 * @create 2018/4/2
 */
public interface ClusterService {

    public List<ClusterDTO> index();

    public Cluster create(ClusterDTO clusterDTO, User user);

    public Cluster update(ClusterDTO clusterDTO);

    public ClusterDTO show(Integer id);

    Integer getClusterSrvCount(Integer clusterId);

    public void remove(Integer id);
}

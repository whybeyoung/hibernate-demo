/**
 * Copyright (C), 科大讯飞股份有限公司
 * FileName: UserServiceImpl
 */
package com.iflytek.iaas.service.impl;

import com.iflytek.iaas.dao.ClusterDao;
import com.iflytek.iaas.domain.Cluster;
import com.iflytek.iaas.dto.ClusterDTO;
import com.iflytek.iaas.dto.UserDTO;
import com.iflytek.iaas.service.ClusterService;
import com.iflytek.iaas.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

/**
 * 〈集群服务〉
 *
 * @author ruizhao3
 * @create 2018/4/10
 */
@Service("ClusterService")
public class ClusterServiceImpl implements ClusterService {
    private Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private ClusterDao clusterDao;


    @Override
    public Page<ClusterDTO> findByNameLike(String name, Integer page, Integer pagesize) {
        Pageable pageable = PageRequest.of(page, pagesize);
        Page<Cluster> clusters = clusterDao.findByNameLike(name, pageable);

        Page<ClusterDTO> dtos =  clusters.map((model)->{
            ClusterDTO dto = new ClusterDTO();
            BeanUtils.copyProperties(model,dto);
            return dto;
        });
        return dtos;
    }

    @Override
    public Boolean saveClusterInfo(ClusterDTO clusterDTO) {
        Cluster cluster = new Cluster();
        BeanUtils.copyProperties(clusterDTO, cluster);
        return clusterDao.save(cluster) != null;
    }
}

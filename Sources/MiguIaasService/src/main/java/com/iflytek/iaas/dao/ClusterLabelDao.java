package com.iflytek.iaas.dao;

import com.iflytek.iaas.domain.ClusterLabel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClusterLabelDao extends JpaRepository<ClusterLabel, Integer> {

    public void deleteByClusterId(Integer clusterId);

    public ClusterLabel findOneByClusterId(Integer clusterId);
}

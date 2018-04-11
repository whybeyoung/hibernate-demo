/**
 * Copyright (C), 科大讯飞股份有限公司
 * FileName: UserRepository
 * Author:   xwliu
 * Date:     2018/4/1 18:30
 * Description: 用户操作dao
 */
package com.iflytek.iaas.dao;

import com.iflytek.iaas.domain.Cluster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 〈集群操作dao〉
 *
 * @author ruizhao3
 * @create 2018/4/1
 */
public interface ClusterDao extends JpaRepository<Cluster,Integer> {

    /**
     * 根据集群名查询集群列表
     * @param name
     * @return
     */
    Page<Cluster> findByNameLike(String name, Pageable pageable);

}

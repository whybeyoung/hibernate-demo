/**
 * Copyright (C), 科大讯飞股份有限公司
 * FileName: UserRepository
 * Author:   xwliu
 * Date:     2018/4/1 18:30
 * Description: 用户操作dao
 */
package com.iflytek.iaas.dao;

import com.iflytek.iaas.domain.Server;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 〈集群操作dao〉
 *
 * @author ruizhao3
 * @create 2018/4/1
 */
public interface ServerDao extends JpaRepository<Server,Integer> {

    /**
     * 根据主机名查询集群列表
     * @param hostname
     * @return
     */
    Server findByHostname(String hostname);

    List<Server> findByClusterIdIsNull();

    List<Server> findByClusterIdIsNullAndIpv4LikeAndHostnameLike(String ipv4, String hostname);

    List<Server> findByClusterId(Integer clusterId);

    List<Server> findAllByIpv4LikeAndHostnameLikeAndOsLike(String ipv4, String hostname, String os);

    Integer countByClusterIdAndStatus(Integer clusterId, boolean valid);

//    update server s set s.cluster_id = -1 where s.cluster_id = :clusterId
    @Modifying
    @Query(value = "UPDATE `migu_paas`.`server` SET `cluster_id`=-1 WHERE `cluster_id`=:clusterId", nativeQuery=true)
    void removeClusterId(@Param(value = "clusterId") Integer clusterId);

    @Modifying
    @Query(value = "update server s set s.cluster_id = :clusterId where id in :serverIds", nativeQuery=true)
    void setClusterIds(@Param(value="serverIds") List serverIds, @Param(value="clusterId") Integer clusterId);

}

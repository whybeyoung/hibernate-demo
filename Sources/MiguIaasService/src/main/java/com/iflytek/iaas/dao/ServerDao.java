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

    List<Server> findByClusterId();

}

/**
 * Copyright (C), 科大讯飞股份有限公司
 * FileName: UserRepository
 * Author:   xwliu
 * Date:     2018/4/1 18:30
 * Description: 用户操作dao
 */
package com.iflytek.iaas.dao;

import com.iflytek.iaas.domain.DeployApp;
import com.iflytek.iaas.domain.Image;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 〈应用操作dao〉
 *
 * @author ruizhao3
 * @create 2018/4/11
 */
public interface DeployAppDao extends JpaRepository<DeployApp,Integer> {

    /**
     * 根据应用名查询集群列表
     * @param name
     * @return
     */
    Page<DeployApp> findByNameLike(String name, Pageable pageable);

}

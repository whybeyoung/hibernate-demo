/**
 * Copyright (C), 科大讯飞股份有限公司
 * FileName: DeployAppDao
 * Author:   xwliu
 * Date:     2018/4/1 18:30
 * Description: 用户操作dao
 */
package com.iflytek.iaas.dao;

import com.iflytek.iaas.domain.DeployApp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 〈应用操作dao〉
 *
 * @author ruizhao3
 * @create 2018/4/11
 */
public interface DeployAppDao extends JpaRepository<DeployApp, Integer> {


    /**
     * 通过应用名查询
     *
     * @param name
     * @return
     */
    List<DeployApp> findByName(String name);


    Integer countByNodePortAndValid(Integer nodePort, boolean valid);

}

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
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * 〈镜像操作dao〉
 *
 * @author ruizhao3
 * @create 2018/4/11
 */
public interface ImageDao extends JpaRepository<Image,Integer> {

    /**
     * 根据镜像名查询集群列表
     * @param name
     * @return
     */
    Page<Image> findByNameLikeAndVersion(String name, String version, Pageable pageable);

    /**
     * 名字Like查询
     * @param name
     * @return
     */
    List<Image> findByNameLikeAndValid(String name, boolean valid);

    /**
     * 通过名字EQ查询
     * @param name
     * @return
     */
    List<Image> findByName(String name);

    /**
     * 通过名字和版本EQ统计
     * @param name
     * @param version
     * @return
     */
    Integer countByNameAndVersionAndValid(String name, String version, boolean valid);



}

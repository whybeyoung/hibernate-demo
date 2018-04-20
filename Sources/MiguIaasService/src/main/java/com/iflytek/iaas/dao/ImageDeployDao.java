/**
 * Copyright (C), 科大讯飞股份有限公司
 * FileName: ImageDeployDao
 * Author:   ruizhao3
 * Date:     2018/4/14 18:30
 * Description: 用户操作dao
 */
package com.iflytek.iaas.dao;

import com.iflytek.iaas.domain.ImageDeploy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 〈镜像部署操作dao〉
 *
 * @author ruizhao3
 * @create 2018/4/14
 */
public interface ImageDeployDao extends JpaRepository<ImageDeploy,Integer> {

    List<ImageDeploy> findAllByAppIdIn(List<Integer> appids);

    List<ImageDeploy> findAllByAppIdInAndValid(List<Integer> appids, boolean valid);

    Integer countByNameAndValid(String name, boolean valid);
}

/**
 * Copyright (C), 科大讯飞股份有限公司
 * FileName: UserRepository
 * Author:   xwliu
 * Date:     2018/4/1 18:30
 * Description: 用户操作dao
 */
package com.iflytek.iaas.dao;

import com.iflytek.iaas.domain.DeployNamespace;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 〈命名空间操作dao〉
 *
 * @author ruizhao3
 * @create 2018/4/20
 */
public interface DeployNamespaceDao extends JpaRepository<DeployNamespace, Integer> {

    /**
     *
     * @param ns
     * @param valid
     * @return
     */
    Integer countByNsAndValid(String ns, boolean valid);
}

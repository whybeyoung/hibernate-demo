/**
 * Copyright (C), 科大讯飞股份有限公司
 * FileName: PermissionDao
 * Author:   xwliu
 * Date:     2018/4/2 15:34
 * Description: 权限Dao
 */
package com.iflytek.iaas.dao;

import com.iflytek.iaas.domain.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 〈权限Dao〉
 *
 * @author xwliu
 * @create 2018/4/2
 */
public interface PermissionDao extends JpaRepository<Permission,Integer>{

}

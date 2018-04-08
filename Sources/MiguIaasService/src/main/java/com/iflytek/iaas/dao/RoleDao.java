/**
 * Copyright (C), 科大讯飞股份有限公司
 * FileName: RoleDao
 * Author:   xwliu
 * Date:     2018/4/2 15:25
 * Description: 角色Dao
 */
package com.iflytek.iaas.dao;

import com.iflytek.iaas.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 〈角色Dao〉
 *
 * @author xwliu
 * @create 2018/4/2
 */
public interface RoleDao extends JpaRepository<Role,Integer>{

}

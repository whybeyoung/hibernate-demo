package com.iflytek.iaas.dao;

import com.iflytek.iaas.domain.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * 〈角色权限关联Dao〉
 *
 * @author xwliu
 * @create 2018/4/2
 */
public interface RolePermissionDao extends JpaRepository<RolePermission,Integer>{

    /**
     * 根据角色id查询权限列表
     * @param roleId
     * @return
     */
    List<RolePermission> findByRoleId(String roleId);
}

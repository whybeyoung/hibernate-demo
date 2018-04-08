/**
 * Copyright (C), 科大讯飞股份有限公司
 * FileName: PermissionServiceImpl
 */
package com.iflytek.iaas.service.impl;

import com.iflytek.iaas.dao.RolePermissionDao;
import com.iflytek.iaas.domain.RolePermission;
import com.iflytek.iaas.service.PermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 〈权限服务〉
 *
 * @author xwliu
 * @create 2018/4/8
 */
@Service("PermissionService")
public class PermissionServiceImpl  implements PermissionService{
    private Logger logger = LoggerFactory.getLogger(PermissionService.class);

    @Autowired
    private RolePermissionDao rolePermissionDao;

    @Override
    public List<String> getPermissionsByRoleId(String roleId) {
        List<RolePermission> rolePermissions = rolePermissionDao.findByRoleId(roleId);
        List<String> permissions = new ArrayList<>();
        for(RolePermission item : rolePermissions){
            permissions.add(item.getPermissionCode());
        }
        return permissions;
    }
}

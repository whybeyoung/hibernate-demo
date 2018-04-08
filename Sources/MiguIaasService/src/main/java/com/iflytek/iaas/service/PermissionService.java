/**
 * Copyright (C), 科大讯飞股份有限公司
 * FileName: PermissionService
 */
package com.iflytek.iaas.service;

import java.util.List;

/**
 * 〈权限服务〉
 *
 * @author xwliu
 * @create 2018/4/8
 */
public interface PermissionService {

    /**
     * 根据角色id获取权限列表
     * @param roleId 角色id
     * @return
     */
    List<String> getPermissionsByRoleId(String roleId);

}

/**
 * Copyright (C), 科大讯飞股份有限公司
 * FileName: IaasRealm
 * Author:   xwliu
 * Date:     2018/4/2 14:01
 * Description: 自定义Realm
 */
package com.iflytek.iaas.shiro.realm;

import com.iflytek.iaas.dto.UserDTO;
import com.iflytek.iaas.service.PermissionService;
import com.iflytek.iaas.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 〈自定义Realm〉
 *
 * @author xwliu
 * @create 2018/4/2
 */
public class IaasRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Autowired
    private PermissionService permissionService;

    /**
     * 角色授权
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String account = (String) principals.fromRealm(getName()).iterator().next();
        UserDTO userDTO = userService.getUserInfoByAuth(account);
        if(userDTO == null){
            return null;
        }else{
            List<String> permissions = permissionService.getPermissionsByRoleId(userDTO.getRoleId());
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            for(String permission: permissions){
                info.addStringPermission(permission);
            }
            return info;
        }
    }

    /**
     * 角色验证
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String account = (String)token.getPrincipal();
        String password = new String((char[])token.getCredentials());
        UserDTO userDTO = userService.getUserInfoByAuth(account);
        if(userDTO == null){
            return null;
        }else{
            if(password.equalsIgnoreCase(userDTO.getPassword())){
                return new SimpleAuthenticationInfo(account, password, this.getName());
            }else{
                return null;
            }
        }
    }
}

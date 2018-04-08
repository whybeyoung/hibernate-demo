/**
 * Copyright (C), 科大讯飞股份有限公司
 * FileName: UserServiceImpl
 */
package com.iflytek.iaas.service.impl;

import com.iflytek.iaas.dao.UserDao;
import com.iflytek.iaas.domain.User;
import com.iflytek.iaas.dto.UserDTO;
import com.iflytek.iaas.service.PermissionService;
import com.iflytek.iaas.service.UserService;
import com.iflytek.iaas.utils.RegularUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 〈用户服务〉
 *
 * @author xwliu
 * @create 2018/4/2
 */
@Service("UserService")
public class UserServiceImpl implements UserService {
    private Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserDao userDao;

    @Autowired
    private PermissionService permissionService;

    @Override
    public UserDTO getUserInfoById(String id) {
        UserDTO userDTO = new UserDTO();
        Optional<User> user = userDao.findById(id);
        if(user.isPresent()){
            BeanUtils.copyProperties(user.get(),userDTO);
            userDTO.setPermissions(permissionService.getPermissionsByRoleId(user.get().getRoleId()));
        }
        return userDTO;
    }

    @Override
    public UserDTO getUserInfoByAuth(String auth) {
        UserDTO userDTO = new UserDTO();
        User user ;
        if(RegularUtils.isEmail(auth)){
            user = userDao.findByEmail(auth);
        }else if(RegularUtils.isPhone(auth)){
            user = userDao.findByPhone(auth);
        }else{
            user = userDao.findByAccount(auth);
        }
        if(user != null){
            BeanUtils.copyProperties(user,userDTO);
            userDTO.setPermissions(permissionService.getPermissionsByRoleId(user.getRoleId()));
        }
        return userDTO;
    }

    @Override
    public Boolean updateUserInfo(UserDTO userDTO) {
        return null;
    }
}

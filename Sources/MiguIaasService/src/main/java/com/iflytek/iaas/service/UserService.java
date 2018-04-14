/**
 * Copyright (C), 科大讯飞股份有限公司
 * FileName: UserService
 * Author:   xwliu
 * Date:     2018/4/1 18:34
 * Description: 用户服务
 */
package com.iflytek.iaas.service;


import com.iflytek.iaas.domain.User;
import com.iflytek.iaas.dto.UserDTO;

/**
 * 〈用户服务接口〉
 *
 * @author xwliu
 * @create 2018/4/1
 */
public interface UserService {

    /**
     * 根据id获取用户信息
     * @param id 用户id
     * @return
     */
    UserDTO getUserInfoById(String id);

    /**
     * 根据账号/邮箱/手机号获取用户信息
     * @param auth  账号/邮箱/手机号
     * @return
     */
    User getUserByAuth(String auth);


    /**
     * 更新用户信息
     * @param userDTO
     * @return
     */
    Boolean updateUserInfo(UserDTO userDTO);


}

/**
 * Copyright (C), 科大讯飞股份有限公司
 * FileName: UserRepository
 * Author:   xwliu
 * Date:     2018/4/1 18:30
 * Description: 用户操作dao
 */
package com.iflytek.iaas.dao;

import com.iflytek.iaas.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 〈用户操作dao〉
 *
 * @author xwliu
 * @create 2018/4/1
 */
public interface UserDao extends JpaRepository<User,String> {

    /**
     * 根据优先查询用户
     * @param email
     * @return
     */
    User findByEmail(String email);

    /**
     * 根据手机号查询用户
     * @param phone
     * @return
     */
    User findByPhone(String phone);

    /**
     * 根据账号查询用户
     * @param account
     * @return
     */
    User findByAccount(String account);
}

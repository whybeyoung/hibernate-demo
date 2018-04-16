/**
 * Copyright (C), 科大讯飞股份有限公司
 * FileName: UserController
 * Author:   xwliu
 * Date:     2018/4/1 18:43
 * Description: 用户controller
 */
package com.iflytek.iaas.controller;

import com.iflytek.iaas.domain.User;
import com.iflytek.iaas.dto.UserDTO;
import com.iflytek.iaas.exception.MiguForbiddenException;
import com.iflytek.iaas.service.UserService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 〈用户controller〉
 *
 * @author xwliu
 * @create 2018/4/1
 */
@Api(value = "User-API", description = "用户信息相关")
@RestController
@RequestMapping(path="/api/v1")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users/current")
    @ResponseBody
    public User current(HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("CURRENT_USER");
        if(user == null) {
            throw new MiguForbiddenException("not login");
        }
        return user;
    }

}

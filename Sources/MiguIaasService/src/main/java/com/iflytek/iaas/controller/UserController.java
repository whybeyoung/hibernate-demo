/**
 * Copyright (C), 科大讯飞股份有限公司
 * FileName: UserController
 * Author:   xwliu
 * Date:     2018/4/1 18:43
 * Description: 用户controller
 */
package com.iflytek.iaas.controller;

import com.iflytek.iaas.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 〈用户controller〉
 *
 * @author xwliu
 * @create 2018/4/1
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/test")
    @ResponseBody
    public String test(){

        return "success";
    }

}

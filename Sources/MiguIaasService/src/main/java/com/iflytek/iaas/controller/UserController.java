/**
 * Copyright (C), 科大讯飞股份有限公司
 * FileName: UserController
 * Author:   xwliu
 * Date:     2018/4/1 18:43
 * Description: 用户controller
 */
package com.iflytek.iaas.controller;

import com.iflytek.iaas.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(value = "User-API", description = "用户信息相关")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "test",notes = "test for demo")
    @GetMapping("/test")
    @ResponseBody
    public String test(){

        return "success";
    }

}

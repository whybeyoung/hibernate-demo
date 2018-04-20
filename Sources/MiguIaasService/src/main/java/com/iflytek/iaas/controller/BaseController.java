package com.iflytek.iaas.controller;

import com.iflytek.iaas.domain.User;

import javax.servlet.http.HttpServletRequest;

public abstract class BaseController {
    //请求成功
    protected static final String SUCCESS = "success";
    //请求失败
    protected static final String FAILED = "failed";

    protected User getCurrentUser(HttpServletRequest request){
        Object user = request.getSession().getAttribute("CURRENT_USER");
        if(user != null){
            return (User) user;
        }
        return new User();
    }
}

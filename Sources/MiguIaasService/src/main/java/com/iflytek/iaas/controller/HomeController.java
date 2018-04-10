/**
 * Copyright (C), 科大讯飞股份有限公司
 * FileName: HomeController
 * Author:   xwliu
 * Date:     2018/4/2 15:04
 * Description: 登录，登出，验证码等Controller
 */
package com.iflytek.iaas.controller;

import com.alibaba.fastjson.JSON;
import com.iflytek.iaas.consts.ReturnCode;
import com.iflytek.iaas.dto.UserDTO;
import com.iflytek.iaas.exception.ControllerException;
import com.iflytek.iaas.service.UserService;
import com.iflytek.iaas.utils.MD5Utils;
import com.iflytek.iaas.utils.VerifyCodeUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 * 〈登录，登出，验证码等Controller〉
 *
 * @author xwliu
 * @create 2018/4/2
 */
@RequestMapping(path="/api/v1")
@RestController
public class HomeController{

    private static final Logger logger = LoggerFactory
            .getLogger(HomeController.class);

    @Autowired
    private UserService userService;

    /**
     * 登录
     * @param request request
     * @param account 账号/邮件/手机号
     * @param pwd 密码
     * @param code 验证码
     * @return
     */
    @PostMapping("/login")
    public String login(HttpServletRequest request, String account,String pwd,String code)throws ControllerException{
        HttpSession session = request.getSession();
        String verCode = (String) session.getAttribute("verCode");

        if (StringUtils.isEmpty(account) || StringUtils.isEmpty(pwd)
                || StringUtils.isEmpty(code)) {
            throw new ControllerException(ReturnCode.PARAM_UNVALID);
        }

        if (StringUtils.isEmpty(verCode) || !verCode.equalsIgnoreCase(code)) {
            throw new ControllerException(ReturnCode.VERIFY_ERROR);
        }
        session.removeAttribute("verCode");

        UserDTO userDTO = userService.getUserInfoByAuth(account);
        if(userDTO == null){
            throw new ControllerException(ReturnCode.ACCOUNT_PWD_ERROR);
        }

        try {
            pwd = MD5Utils.encrypt(pwd, userDTO.getSalt());

        } catch (NoSuchAlgorithmException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            throw new ControllerException(ReturnCode.EXCEPTION);
        }

        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(account, pwd);

        try {
            subject.login(token);
            return JSON.toJSONString(userDTO);

        } catch (Exception e) {
            throw new ControllerException(ReturnCode.ACCOUNT_PWD_ERROR);
        }
    }

    /**
     * 退出登录
     * @return
     */
    @GetMapping("/logout")
    public String logout(){
        SecurityUtils.getSubject().logout();
        return "success";
    }

    /**
     * 获取验证码
     * @param request request
     * @return
     */
    @GetMapping("/verify")
    public String verify(HttpServletRequest request) throws ControllerException{
        // 生成随机字串
        String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
        // 存入会话session
        HttpSession session = request.getSession();
        session.removeAttribute("verCode");
        session.setAttribute("verCode", verifyCode.toLowerCase());
        // 生成图片
        int w = 100, h = 50;
        try {
            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
            VerifyCodeUtils.outputImage(w, h, outStream, verifyCode);
            byte[] buffer = outStream.toByteArray();
            outStream.close();
            String base64Str = Base64.encodeToString(buffer);
            return "data:image/jpeg;base64," + base64Str;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new ControllerException(ReturnCode.VERIFY_FAIL);
        }
    }

}

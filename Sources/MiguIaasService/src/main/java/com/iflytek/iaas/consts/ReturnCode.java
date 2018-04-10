package com.iflytek.iaas.consts;

/**
 * Created by xwliu on 2018/4/3.
 */
public enum ReturnCode {

    SUCCESS("000000", "成功"),
    EXCEPTION("999999", "内部错误"),

    //鉴权类
    AUTH_FAIL("000001","登录失败"),
    VERIFY_FAIL("000002","验证码生成失败"),
    VERIFY_ERROR("000003","验证码不正确"),
    ACCOUNT_PWD_ERROR("000004","账号或密码不正确"),
    ACCOUNT_EXIST("000005","账号已存在"),
    POWER_NOT("000006","没有权限"),
    PHONE_EXIST("000007","手机号已存在"),
    EMAIL_EXIST("000008","邮箱已存在"),

    //集群类

    //主机类

    //镜像类

    //部署类

    //参数类
    PARAM_UNVALID("100001","参数不合法");

    private String code;
    private String msg;

    private ReturnCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

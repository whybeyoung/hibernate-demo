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
    IMAGE_NULL_NAME("300001", "镜像名不能为空"),
    IMAGE_NULL_VERSION("300002", "版本号不能为空"),
    IMAGE_NULL_FTPPATH("300003", "FTP路径不能为空"),
    IMAGEHUB_FAILED_PUSH("300004", "镜像安装到仓库失败"),
    IMAGEHUB_EXISTS("300005", "镜像文件已存在Hub仓库中"),
    IMAGE_NOTESISTS_FTPPATH("300006", "镜像文件路径不存在"),
    IMAGE_FAILED_LOAD("300007", "镜像加载失败"),
    IMAGE_FAILED_REMOVE("300008", "镜像文件删除失败"),

    //部署类
    DEPOY_APP_ILLEGALNAME("400001","应用名为空或不合法,只能由小写字母,数字,短横线组成"),
    DEPOY_APP_EXISTS("400002","应用已存在"),
    DEPOY_NS_NULLNAME("400003","命名空间不能为空"),
    DEPOY_APP_ILLEGALPORT("400004","端口不合法,范围[30000-32767]"),
    DEPOY_UNKNOWN_APP("400006","未指定应用或应用不可用"),
    DEPOY_UNKNOWN_CLUSTER("400007","未指定集群或集群不可用"),
    DEPOY_UNKNOWN_IMAGE("400008","未指定镜像或镜像不可用"),
    DEPOY_UNVALID_PODS("400009","实例数必须大于0"),
    DEPOY_UNVALID_PODSLIMIT("400010","最大最小实例数不能小于1,并且最大实例数不能小与最小实例数"),
    DEPOY_UNVALID_INITCMD("400011","启动命令不能为空"),
    DEPOY_FAILED_IMAGE("400012","镜像部署失败"),
    DEPOY_UNKNOWN_DEPLOYEDIMAGE("400013","未指定已部署镜像或部署镜像不可用"),
    DEPOY_FAILED_DELETEIMAGE("400014","删除镜像部署失败"),
    DEPOY_FAILED_SEVICE("400015","服务部署失败"),
    DEPOY_FAILED_DELETESEVICE("400017","删除服务部署失败"),
    DEPOY_SCALE_ILLEGALPODS("400018","非法的伸缩pods数, 必须为整数且满足最大最小pods数的限制范围"),
    DEPOY_SCALE_FAILED("400019","伸缩失败"),
    DEPOY_PORT_BINDING("400020","该外部端口已被占用"),
    DEPOY_REPEAT_IMAGE("400021","该镜像已经部署"),
    DEPOY_REPEAT_SERVICE("400022","该服务已经启动"),
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

package com.iflytek.iaas.consts;

/**
 * 部署状态
 * desc：-2停止部署，-1未开始，0完成，1正在部署，2其他异常
 * Created by ruizhao3 on 2018/4/18.
 */
public enum  DeployStatus {
    /**
     * 停止部署　-2
     */
    STOPPED(-2),

    /**
     * 未开始  -1
     */
    NOT_START(-1),

    /**
     * 完成  0
     */
    DONE(0),

    /**
     * 正在部署  1
     */
    DEPLOYING(1),

    /**
     * 其他异常　2
     */
    OTHER_EXCEPTION(2);


    private int value;

    private DeployStatus(int value) {
        this.value = value;
    }

    public int val(){
        return this.value;
    }

    public byte byteVal(){
        return (byte) this.value;
    }
}

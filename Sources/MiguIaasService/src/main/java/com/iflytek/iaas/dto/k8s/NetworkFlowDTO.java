/**
 * Copyright (C), 科大讯飞股份有限公司
 * FileName: NetworkFlowDTO
 */
package com.iflytek.iaas.dto.k8s;

/**
 * 〈网络流量DTO〉
 *
 * @author xwliu
 * @create 2018/4/12
 */
public class NetworkFlowDTO {

    /**
     * 接受流量
     */
    private String receiveResult;
    /**
     * 发送流量
     */
    private String transmitResult;
    /**
     * 总流量
     */
    private String totalResult;

    public String getReceiveResult() {
        return receiveResult;
    }

    public void setReceiveResult(String receiveResult) {
        this.receiveResult = receiveResult;
    }

    public String getTransmitResult() {
        return transmitResult;
    }

    public void setTransmitResult(String transmitResult) {
        this.transmitResult = transmitResult;
    }

    public String getTotalResult() {
        return totalResult;
    }

    public void setTotalResult(String totalResult) {
        this.totalResult = totalResult;
    }
}

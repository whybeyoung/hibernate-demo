/**
 * Copyright (C), 科大讯飞股份有限公司
 * FileName: NetworkFlowDTO
 */
package com.iflytek.iaas.dto.k8s;

import com.alibaba.fastjson.JSONArray;

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
    private JSONArray receiveResult;
    /**
     * 发送流量
     */
    private JSONArray transmitResult;
    /**
     * 总流量
     */
    private JSONArray totalResult;

    public JSONArray getReceiveResult() {
        return receiveResult;
    }

    public void setReceiveResult(JSONArray receiveResult) {
        this.receiveResult = receiveResult;
    }

    public JSONArray getTransmitResult() {
        return transmitResult;
    }

    public void setTransmitResult(JSONArray transmitResult) {
        this.transmitResult = transmitResult;
    }

    public JSONArray getTotalResult() {
        return totalResult;
    }

    public void setTotalResult(JSONArray totalResult) {
        this.totalResult = totalResult;
    }
}

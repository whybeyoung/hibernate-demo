/**
 * Copyright (C), 科大讯飞股份有限公司
 * FileName: ServiceDeployInfoDTO
 */
package com.iflytek.iaas.dto.k8s;

import com.iflytek.iaas.consts.K8sAPPType;

import java.util.List;

/**
 * 〈服务部署信息DTO〉
 *
 * @author xwliu
 * @create 2018/4/16
 */
public class ServiceDeployInfoDTO {
    private K8sAPPType type;
    private String ip;
    private int podPort;
    /**
     * 只有type是EXTERNAL时，才需要有servicePort
     */
    private int nodePort;

    /**
     * 部署标签
     */
    private List<LabelDTO> deployLabel;

    public K8sAPPType getType() {
        return type;
    }

    public void setType(K8sAPPType type) {
        this.type = type;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPodPort() {
        return podPort;
    }

    public void setPodPort(int podPort) {
        this.podPort = podPort;
    }

    public int getNodePort() {
        return nodePort;
    }

    public void setNodePort(int nodePort) {
        this.nodePort = nodePort;
    }
}

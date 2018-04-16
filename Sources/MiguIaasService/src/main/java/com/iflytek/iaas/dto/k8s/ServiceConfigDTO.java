/**
 * Copyright (C), 科大讯飞股份有限公司
 * FileName: ServiceConfigDTO
 */
package com.iflytek.iaas.dto.k8s;

import com.iflytek.iaas.consts.K8sAPPType;

import java.util.ArrayList;
import java.util.List;

/**
 * 〈服务部署配置DTO〉
 *
 * @author xwliu
 * @create 2018/4/14
 */
public class ServiceConfigDTO {

    private K8sAPPType type;
    private String serverName;
    private List<String> imgNames;
    private String namespace;
    /**
     * 部署标签，根据部署标签创建服务
     */
    private List<LabelDTO> deployLabels;
    private Integer podPort;
    /**
     * 只有type是EXTERNAL时，才需要有servicePort，只能指定30000-32767之间的值
     */
    private Integer NodePort;

    public K8sAPPType getType() {
        return type;
    }

    public void setType(K8sAPPType type) {
        this.type = type;
    }

    public int getPodPort() {
        return podPort;
    }

    public void setPodPort(int podPort) {
        this.podPort = podPort;
    }

    public Integer getNodePort() {
        return NodePort;
    }

    public void setNodePort(Integer nodePort) {
        NodePort = nodePort;
    }

    public void setDeployLabels(List<LabelDTO> deployLabels) {
        this.deployLabels = deployLabels;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public List<String> getImgNames() {
        return imgNames;
    }

    public void setImgNames(List<String> imgNames) {
        this.imgNames = imgNames;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public List<LabelDTO> getDeployLabels() {
        List<LabelDTO> labels = new ArrayList<>();
        for(String imgName:imgNames){
            LabelDTO labelDTO = new LabelDTO();
            labelDTO.setKey(this.namespace);
            labelDTO.setValue(imgName);
            labels.add(labelDTO);
        }
        return labels;
    }
}

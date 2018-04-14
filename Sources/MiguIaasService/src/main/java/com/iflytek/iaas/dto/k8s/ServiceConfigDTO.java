/**
 * Copyright (C), 科大讯飞股份有限公司
 * FileName: ServiceConfigDTO
 */
package com.iflytek.iaas.dto.k8s;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 〈服务部署配置DTO〉
 *
 * @author xwliu
 * @create 2018/4/14
 */
public class ServiceConfigDTO {

    private String serverName;
    private List<String> imgNames;
    private String namespace;
    private List<LabelDTO> deployLabels;

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

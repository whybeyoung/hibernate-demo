package com.iflytek.iaas.dto;

/**
 *  〈健康检查DTO〉
 *
 * @author ruizhao3
 * @create 2018/4/17
 */
public class K8sHcDTO {

    private String type;
    private String config;

    public K8sHcDTO() {
    }

    public K8sHcDTO(String type, String config) {
        this.type = type;
        this.config = config;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getConfig() {
        return config;
    }

    public void setConfig(String config) {
        this.config = config;
    }
}

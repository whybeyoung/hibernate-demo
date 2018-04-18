/**
 * Copyright (C), 科大讯飞股份有限公司
 * FileName: PodDTO
 */
package com.iflytek.iaas.dto.k8s;

/**
 * 〈podDTO〉
 *
 * @author xwliu
 * @create 2018/4/18
 */
public class PodDTO {
    private String name;
    private String stauts;
    private String podIp;
    private Integer podContainers;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStauts() {
        return stauts;
    }

    public void setStauts(String stauts) {
        this.stauts = stauts;
    }

    public String getPodIp() {
        return podIp;
    }

    public void setPodIp(String podIp) {
        this.podIp = podIp;
    }

    public Integer getPodContainers() {
        return podContainers;
    }

    public void setPodContainers(Integer podContainers) {
        this.podContainers = podContainers;
    }
}

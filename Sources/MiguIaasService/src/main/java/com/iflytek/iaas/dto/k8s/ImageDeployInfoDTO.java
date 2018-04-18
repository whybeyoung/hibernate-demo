/**
 * Copyright (C), 科大讯飞股份有限公司
 * FileName: ImageDeployInfoDTO
 */
package com.iflytek.iaas.dto.k8s;

/**
 * 〈镜像部署信息DTO〉
 *
 * @author xwliu
 * @create 2018/4/16
 */
public class ImageDeployInfoDTO {
    private Boolean available;
    private Integer pods;
    private Integer availablePods;
    private Integer unavailablePods;

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public Integer getPods() {
        return pods;
    }

    public void setPods(Integer pods) {
        this.pods = pods;
    }

    public Integer getAvailablePods() {
        return availablePods;
    }

    public void setAvailablePods(Integer availablePods) {
        this.availablePods = availablePods;
    }

    public Integer getUnavailablePods() {
        return unavailablePods;
    }

    public void setUnavailablePods(Integer unavailablePods) {
        this.unavailablePods = unavailablePods;
    }
}

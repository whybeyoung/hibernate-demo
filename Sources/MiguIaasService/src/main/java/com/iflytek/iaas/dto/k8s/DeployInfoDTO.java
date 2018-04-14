/**
 * Copyright (C), 科大讯飞股份有限公司
 * FileName: DeployInfoDTO
 */
package com.iflytek.iaas.dto.k8s;

/**
 * 〈部署信息DTO〉
 *
 * @author xwliu
 * @create 2018/4/14
 */
public class DeployInfoDTO {
    private boolean available;
    private int pods;
    private int availablePods;
    private int unavailablePods;

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public int getPods() {
        return pods;
    }

    public void setPods(int pods) {
        this.pods = pods;
    }

    public int getAvailablePods() {
        return availablePods;
    }

    public void setAvailablePods(int availablePods) {
        this.availablePods = availablePods;
    }

    public int getUnavailablePods() {
        return unavailablePods;
    }

    public void setUnavailablePods(int unavailablePods) {
        this.unavailablePods = unavailablePods;
    }
}

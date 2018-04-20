package com.iflytek.iaas.vo;


import java.util.List;
/**
 * 〈服务应用部署VO〉
 *
 * @author ruizhao3
 * @create 2018/4/1８
 */
public class ServiceDeployVO {
    private List<Integer> imageDids;
    private Integer appId;

    public List<Integer> getImageDids() {
        return imageDids;
    }

    public void setImageDids(List<Integer> imageDids) {
        this.imageDids = imageDids;
    }

    public Integer getAppId() {
        return appId;
    }

    public void setAppId(Integer appId) {
        this.appId = appId;
    }
}

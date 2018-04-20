package com.iflytek.iaas.dto;

import com.iflytek.iaas.dto.k8s.ImageDeployInfoDTO;

/**
 *  〈镜像部署pods情况　DTO〉
 *
 * @author ruizhao3
 * @create 2018/4/19
 */
public class ImageDeployPodsDTO {

    private Integer imageDid;

    private ImageDeployInfoDTO deployInfo;

    public ImageDeployPodsDTO() {
    }

    public ImageDeployPodsDTO(Integer imageDid, ImageDeployInfoDTO deployInfo) {
        this.imageDid = imageDid;
        this.deployInfo = deployInfo;
    }

    public Integer getImageDid() {
        return imageDid;
    }

    public void setImageDid(Integer imageDid) {
        this.imageDid = imageDid;
    }

    public ImageDeployInfoDTO getDeployInfo() {
        return deployInfo;
    }

    public void setDeployInfo(ImageDeployInfoDTO deployInfo) {
        this.deployInfo = deployInfo;
    }
}

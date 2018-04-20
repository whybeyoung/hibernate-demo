package com.iflytek.iaas.domain;

import javax.persistence.*;
import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "deploy_dispatch_config")
public class DeployDispatchConfig {
    private Integer id;
    private Integer appId;
    private Integer imageId;
    private String positionCode;
    private String clusterId;
    private Integer baseRps;
    private String customRules;
    private String creator;
    private boolean status;
    private boolean valid;
    private String annotation;
    private Date begintime;
    private Date endtime;
    private Date createtime;
    private Date updatetime;

    @Id
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "app_id")
    public Integer getAppId() {
        return appId;
    }

    public void setAppId(Integer appId) {
        this.appId = appId;
    }

    @Basic
    @Column(name = "image_id")
    public Integer getImageId() {
        return imageId;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }

    @Basic
    @Column(name = "position_code")
    public String getPositionCode() {
        return positionCode;
    }

    public void setPositionCode(String positionCode) {
        this.positionCode = positionCode;
    }

    @Basic
    @Column(name = "cluster_id")
    public String getClusterId() {
        return clusterId;
    }

    public void setClusterId(String clusterId) {
        this.clusterId = clusterId;
    }

    @Basic
    @Column(name = "base_rps")
    public Integer getBaseRps() {
        return baseRps;
    }

    public void setBaseRps(Integer baseRps) {
        this.baseRps = baseRps;
    }

    @Basic
    @Column(name = "custom_rules")
    public String getCustomRules() {
        return customRules;
    }

    public void setCustomRules(String customRules) {
        this.customRules = customRules;
    }

    @Basic
    @Column(name = "creator")
    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    @Basic
    @Column(name = "status")
    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Basic
    @Column(name = "valid")
    public boolean getValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    @Basic
    @Column(name = "annotation")
    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    @Basic
    @Column(name = "begintime")
    public Date getBegintime() {
        return begintime;
    }

    public void setBegintime(Date begintime) {
        this.begintime = begintime;
    }

    @Basic
    @Column(name = "endtime")
    public Date getEndtime() {
        return endtime;
    }

    public void setEndtime(Date endtime) {
        this.endtime = endtime;
    }

    @Basic
    @Column(name = "createtime")
    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    @Basic
    @Column(name = "updatetime")
    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeployDispatchConfig that = (DeployDispatchConfig) o;
        return id == that.id &&
                appId == that.appId &&
                imageId == that.imageId &&
                status == that.status &&
                valid == that.valid &&
                Objects.equals(positionCode, that.positionCode) &&
                Objects.equals(clusterId, that.clusterId) &&
                Objects.equals(baseRps, that.baseRps) &&
                Objects.equals(customRules, that.customRules) &&
                Objects.equals(creator, that.creator) &&
                Objects.equals(annotation, that.annotation) &&
                Objects.equals(begintime, that.begintime) &&
                Objects.equals(endtime, that.endtime) &&
                Objects.equals(createtime, that.createtime) &&
                Objects.equals(updatetime, that.updatetime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, appId, imageId, positionCode, clusterId, baseRps, customRules, creator, status, valid, annotation, begintime, endtime, createtime, updatetime);
    }
}

package com.iflytek.iaas.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "deploy_dispatch_config", schema = "migu_iaas", catalog = "")
public class DeployDispatchConfig {
    private int id;
    private int appId;
    private int imageId;
    private String positionCode;
    private String clusterId;
    private Integer baseRps;
    private String customRules;
    private String creator;
    private byte status;
    private byte valid;
    private String annotation;
    private Timestamp begintime;
    private Timestamp endtime;
    private Timestamp createtime;
    private Timestamp updatetime;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "app_id")
    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }

    @Basic
    @Column(name = "image_id")
    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
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
    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    @Basic
    @Column(name = "valid")
    public byte getValid() {
        return valid;
    }

    public void setValid(byte valid) {
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
    public Timestamp getBegintime() {
        return begintime;
    }

    public void setBegintime(Timestamp begintime) {
        this.begintime = begintime;
    }

    @Basic
    @Column(name = "endtime")
    public Timestamp getEndtime() {
        return endtime;
    }

    public void setEndtime(Timestamp endtime) {
        this.endtime = endtime;
    }

    @Basic
    @Column(name = "createtime")
    public Timestamp getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Timestamp createtime) {
        this.createtime = createtime;
    }

    @Basic
    @Column(name = "updatetime")
    public Timestamp getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Timestamp updatetime) {
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

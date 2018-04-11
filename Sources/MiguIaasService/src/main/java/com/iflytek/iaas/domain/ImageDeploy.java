package com.iflytek.iaas.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "image_deploy", schema = "migu_iaas", catalog = "")
public class ImageDeploy {
    private int id;
    private String name;
    private int imageId;
    private int appId;
    private int clusterId;
    private String deployLabel;
    private Byte minPods;
    private Byte maxPods;
    private String positionPods;
    private Byte cpuLimits;
    private Integer memoryLimits;
    private Byte simultUpdates;
    private Integer timeOut;
    private Byte uniqueDeploy;
    private Byte healthCheck;
    private String envs;
    private String initCmd;
    private String mountDirs;
    private String deployType;
    private Byte deployStatus;
    private Byte autoDispatch;
    private Byte valid;
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
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
    @Column(name = "app_id")
    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
    }

    @Basic
    @Column(name = "cluster_id")
    public int getClusterId() {
        return clusterId;
    }

    public void setClusterId(int clusterId) {
        this.clusterId = clusterId;
    }

    @Basic
    @Column(name = "deploy_label")
    public String getDeployLabel() {
        return deployLabel;
    }

    public void setDeployLabel(String deployLabel) {
        this.deployLabel = deployLabel;
    }

    @Basic
    @Column(name = "min_pods")
    public Byte getMinPods() {
        return minPods;
    }

    public void setMinPods(Byte minPods) {
        this.minPods = minPods;
    }

    @Basic
    @Column(name = "max_pods")
    public Byte getMaxPods() {
        return maxPods;
    }

    public void setMaxPods(Byte maxPods) {
        this.maxPods = maxPods;
    }

    @Basic
    @Column(name = "position_pods")
    public String getPositionPods() {
        return positionPods;
    }

    public void setPositionPods(String positionPods) {
        this.positionPods = positionPods;
    }

    @Basic
    @Column(name = "cpu_limits")
    public Byte getCpuLimits() {
        return cpuLimits;
    }

    public void setCpuLimits(Byte cpuLimits) {
        this.cpuLimits = cpuLimits;
    }

    @Basic
    @Column(name = "memory_limits")
    public Integer getMemoryLimits() {
        return memoryLimits;
    }

    public void setMemoryLimits(Integer memoryLimits) {
        this.memoryLimits = memoryLimits;
    }

    @Basic
    @Column(name = "simult_updates")
    public Byte getSimultUpdates() {
        return simultUpdates;
    }

    public void setSimultUpdates(Byte simultUpdates) {
        this.simultUpdates = simultUpdates;
    }

    @Basic
    @Column(name = "time_out")
    public Integer getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(Integer timeOut) {
        this.timeOut = timeOut;
    }

    @Basic
    @Column(name = "unique_deploy")
    public Byte getUniqueDeploy() {
        return uniqueDeploy;
    }

    public void setUniqueDeploy(Byte uniqueDeploy) {
        this.uniqueDeploy = uniqueDeploy;
    }

    @Basic
    @Column(name = "health_check")
    public Byte getHealthCheck() {
        return healthCheck;
    }

    public void setHealthCheck(Byte healthCheck) {
        this.healthCheck = healthCheck;
    }

    @Basic
    @Column(name = "envs")
    public String getEnvs() {
        return envs;
    }

    public void setEnvs(String envs) {
        this.envs = envs;
    }

    @Basic
    @Column(name = "init_cmd")
    public String getInitCmd() {
        return initCmd;
    }

    public void setInitCmd(String initCmd) {
        this.initCmd = initCmd;
    }

    @Basic
    @Column(name = "mount_dirs")
    public String getMountDirs() {
        return mountDirs;
    }

    public void setMountDirs(String mountDirs) {
        this.mountDirs = mountDirs;
    }

    @Basic
    @Column(name = "deploy_type")
    public String getDeployType() {
        return deployType;
    }

    public void setDeployType(String deployType) {
        this.deployType = deployType;
    }

    @Basic
    @Column(name = "deploy_status")
    public Byte getDeployStatus() {
        return deployStatus;
    }

    public void setDeployStatus(Byte deployStatus) {
        this.deployStatus = deployStatus;
    }

    @Basic
    @Column(name = "auto_dispatch")
    public Byte getAutoDispatch() {
        return autoDispatch;
    }

    public void setAutoDispatch(Byte autoDispatch) {
        this.autoDispatch = autoDispatch;
    }

    @Basic
    @Column(name = "valid")
    public Byte getValid() {
        return valid;
    }

    public void setValid(Byte valid) {
        this.valid = valid;
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
        ImageDeploy that = (ImageDeploy) o;
        return id == that.id &&
                imageId == that.imageId &&
                appId == that.appId &&
                clusterId == that.clusterId &&
                Objects.equals(name, that.name) &&
                Objects.equals(deployLabel, that.deployLabel) &&
                Objects.equals(minPods, that.minPods) &&
                Objects.equals(maxPods, that.maxPods) &&
                Objects.equals(positionPods, that.positionPods) &&
                Objects.equals(cpuLimits, that.cpuLimits) &&
                Objects.equals(memoryLimits, that.memoryLimits) &&
                Objects.equals(simultUpdates, that.simultUpdates) &&
                Objects.equals(timeOut, that.timeOut) &&
                Objects.equals(uniqueDeploy, that.uniqueDeploy) &&
                Objects.equals(healthCheck, that.healthCheck) &&
                Objects.equals(envs, that.envs) &&
                Objects.equals(initCmd, that.initCmd) &&
                Objects.equals(mountDirs, that.mountDirs) &&
                Objects.equals(deployType, that.deployType) &&
                Objects.equals(deployStatus, that.deployStatus) &&
                Objects.equals(autoDispatch, that.autoDispatch) &&
                Objects.equals(valid, that.valid) &&
                Objects.equals(createtime, that.createtime) &&
                Objects.equals(updatetime, that.updatetime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, imageId, appId, clusterId, deployLabel, minPods, maxPods, positionPods, cpuLimits, memoryLimits, simultUpdates, timeOut, uniqueDeploy, healthCheck, envs, initCmd, mountDirs, deployType, deployStatus, autoDispatch, valid, createtime, updatetime);
    }
}

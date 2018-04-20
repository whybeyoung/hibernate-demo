package com.iflytek.iaas.dto;


import java.util.Date;

/**
 *  〈镜像部署DTO〉
 *
 * @author ruizhao3
 * @create 2018/4/17
 */
public class ImageDeployDTO {

    private Integer id;
    private String name;
    private String imageName;
    private Integer imageId;
    private Integer appId;
    private Integer clusterId;
    private String deployLabel;
    private Integer minPods;
    private Integer maxPods;
    private Integer pods;
    //可用pods, query from k8s
    private Integer availablePods;
    private Integer podContainers;
    private Integer containerPort;
    private Integer cpuLimits;
    private Integer memoryLimits;
    private Integer simultUpdates;
    private Integer timeOut;
    private boolean uniqueDeploy;
    private String healthCheck;
    private String envs;
    private String initCmd;
    private String mountDirs;
    private String deployType;
    private Byte deployStatus;
    private boolean autoDispatch;
    private boolean valid;
    private Date createtime;
    private Date updatetime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public Integer getImageId() {
        return imageId;
    }

    public void setImageId(Integer imageId) {
        this.imageId = imageId;
    }

    public Integer getAppId() {
        return appId;
    }

    public void setAppId(Integer appId) {
        this.appId = appId;
    }

    public Integer getClusterId() {
        return clusterId;
    }

    public void setClusterId(Integer clusterId) {
        this.clusterId = clusterId;
    }

    public String getDeployLabel() {
        return deployLabel;
    }

    public void setDeployLabel(String deployLabel) {
        this.deployLabel = deployLabel;
    }

    public Integer getMinPods() {
        return minPods;
    }

    public void setMinPods(Integer minPods) {
        this.minPods = minPods;
    }

    public Integer getMaxPods() {
        return maxPods;
    }

    public void setMaxPods(Integer maxPods) {
        this.maxPods = maxPods;
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

    public Integer getPodContainers() {
        return podContainers;
    }

    public void setPodContainers(Integer podContainers) {
        this.podContainers = podContainers;
    }

    public Integer getContainerPort() {
        return containerPort;
    }

    public void setContainerPort(Integer containerPort) {
        this.containerPort = containerPort;
    }

    public Integer getCpuLimits() {
        return cpuLimits;
    }

    public void setCpuLimits(Integer cpuLimits) {
        this.cpuLimits = cpuLimits;
    }

    public Integer getMemoryLimits() {
        return memoryLimits;
    }

    public void setMemoryLimits(Integer memoryLimits) {
        this.memoryLimits = memoryLimits;
    }

    public Integer getSimultUpdates() {
        return simultUpdates;
    }

    public void setSimultUpdates(Integer simultUpdates) {
        this.simultUpdates = simultUpdates;
    }

    public Integer getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(Integer timeOut) {
        this.timeOut = timeOut;
    }

    public boolean isUniqueDeploy() {
        return uniqueDeploy;
    }

    public void setUniqueDeploy(boolean uniqueDeploy) {
        this.uniqueDeploy = uniqueDeploy;
    }

    public String getHealthCheck() {
        return healthCheck;
    }

    public void setHealthCheck(String healthCheck) {
        this.healthCheck = healthCheck;
    }

    public String getEnvs() {
        return envs;
    }

    public void setEnvs(String envs) {
        this.envs = envs;
    }

    public String getInitCmd() {
        return initCmd;
    }

    public void setInitCmd(String initCmd) {
        this.initCmd = initCmd;
    }

    public String getMountDirs() {
        return mountDirs;
    }

    public void setMountDirs(String mountDirs) {
        this.mountDirs = mountDirs;
    }

    public String getDeployType() {
        return deployType;
    }

    public void setDeployType(String deployType) {
        this.deployType = deployType;
    }

    public Byte getDeployStatus() {
        return deployStatus;
    }

    public void setDeployStatus(Byte deployStatus) {
        this.deployStatus = deployStatus;
    }

    public boolean isAutoDispatch() {
        return autoDispatch;
    }

    public void setAutoDispatch(boolean autoDispatch) {
        this.autoDispatch = autoDispatch;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }
}

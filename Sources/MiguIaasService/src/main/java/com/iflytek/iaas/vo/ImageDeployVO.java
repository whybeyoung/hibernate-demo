package com.iflytek.iaas.vo;


import com.iflytek.iaas.dto.k8s.EnvDTO;
import com.iflytek.iaas.dto.k8s.MountVolumeDTO;

import java.util.List;

/**
 * 〈镜像应用VO〉
 *
 * @author ruizhao3
 * @create 2018/4/16
 */
public class ImageDeployVO {
    private Integer imageId;
    private Integer appId;
    private Integer clusterId;
    //default 1
    private Integer minPods = 1;
    private Integer maxPods;
    private Integer pods;
    //default 1
    private Integer podContainers = 1;
    private Integer containerPort;
    private Integer cpuLimits;
    private Integer memoryLimits;
    private Integer simultUpdates;
    //default 120 seconds
    private Integer timeOut = 120;
    private boolean uniqueDeploy;
    private String healthCheck;
    private List<EnvDTO> envs;
    private String initCmd;
    private List<MountVolumeDTO> mountDirs;
    private String deployType;


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

    public List<EnvDTO> getEnvs() {
        return envs;
    }

    public void setEnvs(List<EnvDTO> envs) {
        this.envs = envs;
    }

    public String getInitCmd() {
        return initCmd;
    }

    public void setInitCmd(String initCmd) {
        this.initCmd = initCmd;
    }

    public List<MountVolumeDTO> getMountDirs() {
        return mountDirs;
    }

    public void setMountDirs(List<MountVolumeDTO> mountDirs) {
        this.mountDirs = mountDirs;
    }

    public String getDeployType() {
        return deployType;
    }

    public void setDeployType(String deployType) {
        this.deployType = deployType;
    }

}

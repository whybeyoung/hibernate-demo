/**
 * Copyright (C), 科大讯飞股份有限公司
 * FileName: DeployConfigDTO
 */
package com.iflytek.iaas.dto.k8s;

import java.util.List;

/**
 * 〈部署参数〉
 *
 * @author xwliu
 * @create 2018/4/10
 */
public class DeployConfigDTO {

    private String imgName;
    private String namespace;
    private String imgPath;
    /**
     * 部署标签
     */
    private LabelDTO deployLabel;
    private String initCmd;
    private List<EnvDTO> envs;
    private List<MountVolumeDTO> mountDirs;
    private LabelDTO serverLabel;
    private int podContainers;
    private int pods;
    private int minPods;
    private int maxPods;
    private String memoryLimits;
    private String cpuLimits;
    private boolean uniqueDeploy;
    private int timeOut;
    private boolean healthCheck;

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public int getPodContainers() {
        if(this.podContainers ==0 ){
            this.podContainers = 1;
        }
        return podContainers;
    }

    public void setPodContainers(int podContainers) {
        this.podContainers = podContainers;
    }

    public LabelDTO getDeployLabel() {
        return deployLabel;
    }

    public void setDeployLabel(LabelDTO deployLabel) {
        this.deployLabel = deployLabel;
    }

    public String getInitCmd() {
        return initCmd;
    }

    public void setInitCmd(String initCmd) {
        this.initCmd = initCmd;
    }

    public List<EnvDTO> getEnvs() {
        return envs;
    }

    public void setEnvs(List<EnvDTO> envs) {
        this.envs = envs;
    }

    public List<MountVolumeDTO> getMountDirs() {
        return mountDirs;
    }

    public void setMountDirs(List<MountVolumeDTO> mountDirs) {
        this.mountDirs = mountDirs;
    }

    public LabelDTO getServerLabel() {
        return serverLabel;
    }

    public void setServerLabel(LabelDTO serverLabel) {
        this.serverLabel = serverLabel;
    }

    public int getPods() {
        return pods;
    }

    public void setPods(int pods) {
        this.pods = pods;
    }

    public int getMinPods() {
        return minPods;
    }

    public void setMinPods(int minPods) {
        this.minPods = minPods;
    }

    public int getMaxPods() {
        return maxPods;
    }

    public void setMaxPods(int maxPods) {
        this.maxPods = maxPods;
    }

    public String getMemoryLimits() {
        return memoryLimits;
    }

    public void setMemoryLimits(String memoryLimits) {
        this.memoryLimits = memoryLimits;
    }

    public String getCpuLimits() {
        return cpuLimits;
    }

    public void setCpuLimits(String cpuLimits) {
        this.cpuLimits = cpuLimits;
    }

    public boolean isUniqueDeploy() {
        return uniqueDeploy;
    }

    public void setUniqueDeploy(boolean uniqueDeploy) {
        this.uniqueDeploy = uniqueDeploy;
    }

    public int getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(int timeOut) {
        this.timeOut = timeOut;
    }

    public boolean isHealthCheck() {
        return healthCheck;
    }

    public void setHealthCheck(boolean healthCheck) {
        this.healthCheck = healthCheck;
    }
}

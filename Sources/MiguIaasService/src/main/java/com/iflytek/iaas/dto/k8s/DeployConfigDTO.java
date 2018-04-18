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

    private String imgDeployName;
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
    private Integer podContainers;
    private Integer pods;
    private Integer containerPort;
    private Long memoryLimits;
    private Integer cpuLimits;
    private Boolean uniqueDeploy;
    private Integer timeOut;
    private String healthCheckExec;

    public String getImgDeployName() {
        return imgDeployName;
    }

    public void setImgDeployName(String imgDeployName) {
        this.imgDeployName = imgDeployName;
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

    public Integer getPodContainers() {
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


    public void setMemoryLimits(long memoryLimits) {
        this.memoryLimits = memoryLimits;
    }

    public void setPodContainers(Integer podContainers) {
        this.podContainers = podContainers;
    }

    public Integer getPods() {
        return pods;
    }

    public void setPods(Integer pods) {
        this.pods = pods;
    }

    public Integer getContainerPort() {
        return containerPort;
    }

    public void setContainerPort(Integer containerPort) {
        this.containerPort = containerPort;
    }

    public Long getMemoryLimits() {
        return memoryLimits;
    }

    public void setMemoryLimits(Long memoryLimits) {
        this.memoryLimits = memoryLimits;
    }

    public Integer getCpuLimits() {
        return cpuLimits;
    }

    public void setCpuLimits(Integer cpuLimits) {
        this.cpuLimits = cpuLimits;
    }

    public Boolean getUniqueDeploy() {
        return uniqueDeploy;
    }

    public void setUniqueDeploy(Boolean uniqueDeploy) {
        this.uniqueDeploy = uniqueDeploy;
    }

    public Integer getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(Integer timeOut) {
        this.timeOut = timeOut;
    }

    public String getHealthCheckExec() {
        return healthCheckExec;
    }

    public void setHealthCheckExec(String healthCheckExec) {
        this.healthCheckExec = healthCheckExec;
    }
}

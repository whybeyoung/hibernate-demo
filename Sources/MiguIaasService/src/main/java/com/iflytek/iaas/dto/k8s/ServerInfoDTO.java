/**
 * Copyright (C), 科大讯飞股份有限公司
 * FileName: ServerInfoDTO
 */
package com.iflytek.iaas.dto.k8s;

import com.iflytek.iaas.domain.Server;
import org.springframework.beans.BeanUtils;

import java.util.Date;
import java.util.Map;
import java.util.Objects;

/**
 * 〈主机信息〉
 *
 * @author xwliu
 * @create 2018/4/11
 */
public class ServerInfoDTO {

    private Integer id;
    private String uid;
    private String ipv4;
    private String ipv6;
    private String hostname;
    private String sn;
    private String os;
    private String kernel;
    private String disk;
    private String cpu;
    private String memory;
    private boolean status;
    private boolean valid = true;
    private String annotation;
    private String positionCode;
    private String dockerVersion;
    private Map<String, String> labels;
    private Integer clusterId;

    public Server toServer() {
        Server s = new Server();
        BeanUtils.copyProperties(this, s);
        return s;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIpv4() {
        return ipv4;
    }

    public void setIpv4(String ipv4) {
        this.ipv4 = ipv4;
    }

    public String getIpv6() {
        return ipv6;
    }

    public void setIpv6(String ipv6) {
        this.ipv6 = ipv6;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    public String getKernel() {
        return kernel;
    }

    public void setKernel(String kernel) {
        this.kernel = kernel;
    }

    public String getDisk() {
        return disk;
    }

    public void setDisk(String disk) {
        this.disk = disk;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getMemory() {
        return memory;
    }

    public void setMemory(String memory) {
        this.memory = memory;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    public String getPositionCode() {
        return positionCode;
    }

    public void setPositionCode(String positionCode) {
        this.positionCode = positionCode;
    }

    public String getDockerVersion() {
        return dockerVersion;
    }

    public void setDockerVersion(String dockerVersion) {
        this.dockerVersion = dockerVersion;
    }

    public Integer getClusterId() {
        return clusterId;
    }

    public void setClusterId(Integer clusterId) {
        this.clusterId = clusterId;
    }

    public Map<String, String> getLabels() {
        return labels;
    }

    public void setLabels(Map<String, String> labels) {
        this.labels = labels;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public boolean getValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServerInfoDTO that = (ServerInfoDTO) o;
        return Objects.equals(hostname, that.hostname);
    }

    @Override
    public int hashCode() {

        return Objects.hash(hostname);
    }
}

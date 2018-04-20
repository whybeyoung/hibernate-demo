package com.iflytek.iaas.dto;

import java.util.Date;
import java.util.List;

/**
 * 〈部署应用DTO〉
 *
 * @author ruizhao3
 * @create 2018/4/16
 */
public class DeployAppDTO {

    private Integer id;
    private String name;
    private String creator;
    private String annotation;
    private boolean status;
    private boolean valid;
    private String namespace;
    private String ip;
    private String hostname;
    private Integer apptype;
    private Integer nodePort;
    private Integer podPort;
    private Date createtime;
    private Date updatetime;
    private List<ImageDeployDTO> deployImages;

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

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public Integer getApptype() {
        return apptype;
    }

    public void setApptype(Integer apptype) {
        this.apptype = apptype;
    }

    public Integer getNodePort() {
        return nodePort;
    }

    public void setNodePort(Integer nodePort) {
        this.nodePort = nodePort;
    }

    public Integer getPodPort() {
        return podPort;
    }

    public void setPodPort(Integer podPort) {
        this.podPort = podPort;
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

    public List<ImageDeployDTO> getDeployImages() {
        return deployImages;
    }

    public void setDeployImages(List<ImageDeployDTO> deployImages) {
        this.deployImages = deployImages;
    }
}

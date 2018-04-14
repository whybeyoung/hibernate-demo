package com.iflytek.iaas.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "deploy_app")
public class DeployApp {
    private Integer id;
    private String name;
    private String creator;
    private String annotation;
    private boolean status;
    private boolean valid;
    private String namespace;
    private String ip;
    private String port;
    private String hostname;
    private Byte isService;
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
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
    @Column(name = "annotation")
    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
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
    @Column(name = "namespace")
    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
    }

    @Basic
    @Column(name = "ip")
    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    @Basic
    @Column(name = "port")
    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    @Basic
    @Column(name = "hostname")
    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    @Basic
    @Column(name = "is_service")
    public Byte getIsService() {
        return isService;
    }

    public void setIsService(Byte isService) {
        this.isService = isService;
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
        DeployApp deployApp = (DeployApp) o;
        return id == deployApp.id &&
                status == deployApp.status &&
                valid == deployApp.valid &&
                Objects.equals(name, deployApp.name) &&
                Objects.equals(creator, deployApp.creator) &&
                Objects.equals(annotation, deployApp.annotation) &&
                Objects.equals(namespace, deployApp.namespace) &&
                Objects.equals(ip, deployApp.ip) &&
                Objects.equals(port, deployApp.port) &&
                Objects.equals(hostname, deployApp.hostname) &&
                Objects.equals(isService, deployApp.isService) &&
                Objects.equals(createtime, deployApp.createtime) &&
                Objects.equals(updatetime, deployApp.updatetime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, creator, annotation, status, valid, namespace, ip, port, hostname, isService, createtime, updatetime);
    }
}

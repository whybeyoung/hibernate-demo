package com.iflytek.iaas.domain;


import com.iflytek.iaas.dto.k8s.ServerInfoDTO;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "server")
public class Server implements Serializable {
    private static final long serialVersionUID = -2536926830696056814L;
    private Integer id;
    private String ipv4;
    private String ipv6;
    private String hostname;
    private String sn;
    private String os;
    private String kernel;
    private String disk;
    private String memory;
    private boolean status;
    private boolean valid;
    private String annotation;
    private String positionCode;
    private String dockerVersion;
    private Integer clusterId;
    private Date createtime;
    private Date updatetime;
//    @ManyToOne
//    @JoinColumn(name = "cluster_id")
//    private Cluster cluster;

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "ipv4")
    public String getIpv4() {
        return ipv4;
    }

    public void setIpv4(String ipv4) {
        this.ipv4 = ipv4;
    }

    @Basic
    @Column(name = "ipv6")
    public String getIpv6() {
        return ipv6;
    }

    public void setIpv6(String ipv6) {
        this.ipv6 = ipv6;
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
    @Column(name = "sn")
    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    @Basic
    @Column(name = "os")
    public String getOs() {
        return os;
    }

    public void setOs(String os) {
        this.os = os;
    }

    @Basic
    @Column(name = "kernel")
    public String getKernel() {
        return kernel;
    }

    public void setKernel(String kernel) {
        this.kernel = kernel;
    }

    @Basic
    @Column(name = "disk")
    public String getDisk() {
        return disk;
    }

    public void setDisk(String disk) {
        this.disk = disk;
    }

    @Basic
    @Column(name = "memory")
    public String getMemory() {
        return memory;
    }

    public void setMemory(String memory) {
        this.memory = memory;
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
    @Column(name = "position_code")
    public String getPositionCode() {
        return positionCode;
    }

    public void setPositionCode(String positionCode) {
        this.positionCode = positionCode;
    }

    @Basic
    @Column(name = "docker_version")
    public String getDockerVersion() {
        return dockerVersion;
    }

    public void setDockerVersion(String dockerVersion) {
        this.dockerVersion = dockerVersion;
    }

    @Basic
    @Column(name = "cluster_id")
    public Integer getClusterId() {
        return clusterId;
    }

    public void setClusterId(Integer clusterId) {
        this.clusterId = clusterId;
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
        Server server = (Server) o;
        return id.equals(server.id) &&
                status == server.status &&
                valid == server.valid &&
                Objects.equals(ipv4, server.ipv4) &&
                Objects.equals(ipv6, server.ipv6) &&
                Objects.equals(hostname, server.hostname) &&
                Objects.equals(sn, server.sn) &&
                Objects.equals(os, server.os) &&
                Objects.equals(kernel, server.kernel) &&
                Objects.equals(disk, server.disk) &&
                Objects.equals(memory, server.memory) &&
                Objects.equals(annotation, server.annotation) &&
                Objects.equals(positionCode, server.positionCode) &&
                Objects.equals(dockerVersion, server.dockerVersion) &&
                Objects.equals(clusterId, server.clusterId) &&
                Objects.equals(createtime, server.createtime) &&
                Objects.equals(updatetime, server.updatetime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, ipv4, ipv6, hostname, sn, os, kernel, disk, memory, status, valid, annotation, positionCode, dockerVersion, clusterId, createtime, updatetime);
    }

    public ServerInfoDTO toServerInfoDTO() {
        ServerInfoDTO serverInfoDTO = new ServerInfoDTO();
        BeanUtils.copyProperties(this, serverInfoDTO);
        return serverInfoDTO;
    }

//    public Cluster getCluster() {
//        return cluster;
//    }
//
//    public void setCluster(Cluster cluster) {
//        this.cluster = cluster;
//    }
}

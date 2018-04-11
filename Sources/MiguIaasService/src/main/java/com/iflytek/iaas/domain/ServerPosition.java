package com.iflytek.iaas.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "server_position", schema = "migu_iaas", catalog = "")
public class ServerPosition {
    private int id;
    private String code;
    private String name;
    private String ftpPath;
    private String containerImg;
    private String imgRegistry;
    private String etcdApi;
    private Timestamp createtime;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "code")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
    @Column(name = "ftp_path")
    public String getFtpPath() {
        return ftpPath;
    }

    public void setFtpPath(String ftpPath) {
        this.ftpPath = ftpPath;
    }

    @Basic
    @Column(name = "container_img")
    public String getContainerImg() {
        return containerImg;
    }

    public void setContainerImg(String containerImg) {
        this.containerImg = containerImg;
    }

    @Basic
    @Column(name = "img_registry")
    public String getImgRegistry() {
        return imgRegistry;
    }

    public void setImgRegistry(String imgRegistry) {
        this.imgRegistry = imgRegistry;
    }

    @Basic
    @Column(name = "etcd_api")
    public String getEtcdApi() {
        return etcdApi;
    }

    public void setEtcdApi(String etcdApi) {
        this.etcdApi = etcdApi;
    }

    @Basic
    @Column(name = "createtime")
    public Timestamp getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Timestamp createtime) {
        this.createtime = createtime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServerPosition that = (ServerPosition) o;
        return id == that.id &&
                Objects.equals(code, that.code) &&
                Objects.equals(name, that.name) &&
                Objects.equals(ftpPath, that.ftpPath) &&
                Objects.equals(containerImg, that.containerImg) &&
                Objects.equals(imgRegistry, that.imgRegistry) &&
                Objects.equals(etcdApi, that.etcdApi) &&
                Objects.equals(createtime, that.createtime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, code, name, ftpPath, containerImg, imgRegistry, etcdApi, createtime);
    }
}

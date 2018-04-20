package com.iflytek.iaas.domain;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * 〈镜像实体〉
 *
 * @author ruizhao3
 * @create 2018/4/10
 */
@Entity
@Table(name="image")
@DynamicInsert
@DynamicUpdate
public class Image implements Serializable{
    private static final long serialVersionUID = -645029156901008040L;
    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name = "id")
    private Integer id;
    private String name;
    private String version;
    private String annotation;
    private String creator;
    private boolean valid;
    private String gitPath;
    private String ftpPath;
    private String hubPath;
    private Date createtime;
    private Date updatetime;


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
    @Column(name = "version")
    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
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
    @Column(name = "creator")
    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
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
    @Column(name = "git_path")
    public String getGitPath() {
        return gitPath;
    }

    public void setGitPath(String gitPath) {
        this.gitPath = gitPath;
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
    @Column(name = "hub_path")
    public String getHubPath() {
        return hubPath;
    }

    public void setHubPath(String hubPath) {
        this.hubPath = hubPath;
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
        Image image = (Image) o;
        return id.equals(image.id) &&
                Objects.equals(name, image.name) &&
                Objects.equals(version, image.version) &&
                Objects.equals(annotation, image.annotation) &&
                Objects.equals(creator, image.creator) &&
                Objects.equals(valid, image.valid) &&
                Objects.equals(gitPath, image.gitPath) &&
                Objects.equals(ftpPath, image.ftpPath) &&
                Objects.equals(hubPath, image.hubPath) &&
                Objects.equals(createtime, image.createtime) &&
                Objects.equals(updatetime, image.updatetime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, version, annotation, creator, valid, gitPath, ftpPath, hubPath, createtime, updatetime);
    }
}

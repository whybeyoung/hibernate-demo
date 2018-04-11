package com.iflytek.iaas.domain;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
public class Image {
    private int id;
    private String name;
    private String version;
    private String annotation;
    private String creator;
    private Byte valid;
    private String gitPath;
    private String ftpPath;
    private Timestamp createtime;
    private Timestamp updatetime;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
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
    public Byte getValid() {
        return valid;
    }

    public void setValid(Byte valid) {
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
    @Column(name = "createtime")
    public Timestamp getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Timestamp createtime) {
        this.createtime = createtime;
    }

    @Basic
    @Column(name = "updatetime")
    public Timestamp getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Timestamp updatetime) {
        this.updatetime = updatetime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Image image = (Image) o;
        return id == image.id &&
                Objects.equals(name, image.name) &&
                Objects.equals(version, image.version) &&
                Objects.equals(annotation, image.annotation) &&
                Objects.equals(creator, image.creator) &&
                Objects.equals(valid, image.valid) &&
                Objects.equals(gitPath, image.gitPath) &&
                Objects.equals(ftpPath, image.ftpPath) &&
                Objects.equals(createtime, image.createtime) &&
                Objects.equals(updatetime, image.updatetime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, version, annotation, creator, valid, gitPath, ftpPath, createtime, updatetime);
    }
}

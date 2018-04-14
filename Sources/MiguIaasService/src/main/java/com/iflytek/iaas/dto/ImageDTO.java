package com.iflytek.iaas.dto;

import java.util.Date;

/**
 * 〈镜像DTO〉
 *
 * @author ruizhao3
 * @create 2018/4/11
 */
public class ImageDTO {

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

    public ImageDTO() {
    }

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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getAnnotation() {
        return annotation;
    }

    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public String getGitPath() {
        return gitPath;
    }

    public void setGitPath(String gitPath) {
        this.gitPath = gitPath;
    }

    public String getFtpPath() {
        return ftpPath;
    }

    public void setFtpPath(String ftpPath) {
        this.ftpPath = ftpPath;
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

    public String getHubPath() {
        return hubPath;
    }

    public void setHubPath(String hubPath) {
        this.hubPath = hubPath;
    }
}

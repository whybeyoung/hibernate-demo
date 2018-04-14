package com.iflytek.iaas.vo;

/**
 * 〈镜像VO〉
 *
 * @author ruizhao3
 * @create 2018/4/11
 */
public class ImageVO {

    private String name;
    private String version;
    private String annotation;
    private String creator;
    private String ftpPath;

    public ImageVO() {
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

    public String getFtpPath() {
        return ftpPath;
    }

    public void setFtpPath(String ftpPath) {
        this.ftpPath = ftpPath;
    }
}

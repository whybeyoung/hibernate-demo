package com.iflytek.iaas.vo;

/**
 * 〈部署应用VO〉
 *
 * @author ruizhao3
 * @create 2018/4/16
 */
public class DeployAppVO {

    private String name;
    private String creator;
    private String annotation;
    private String namespace;
    private Integer apptype;
    private Integer nodePort;
    private Integer podPort;

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

    public String getNamespace() {
        return namespace;
    }

    public void setNamespace(String namespace) {
        this.namespace = namespace;
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

    @Override
    public String toString() {
        return "DeployAppVO{" +
                "name='" + name + '\'' +
                ", creator='" + creator + '\'' +
                ", annotation='" + annotation + '\'' +
                ", namespace='" + namespace + '\'' +
                ", apptype=" + apptype +
                ", nodePort=" + nodePort +
                ", podPort=" + podPort +
                '}';
    }
}

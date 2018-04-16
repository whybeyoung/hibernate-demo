package com.iflytek.iaas.dto;

import com.iflytek.iaas.domain.Cluster;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * 〈集群DTO〉
 *
 * @author ruizhao3
 * @create 2018/4/10
 */
public class ClusterDTO{

    private Integer id;
    private String name;
    private String annotation;
    private String creator;
    private boolean valid;
    private Date createtime;
    private String labelName;

    public Cluster toCluster() {
        Cluster cluster = new Cluster();
        BeanUtils.copyProperties(this, cluster);
        return cluster;
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

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }
}

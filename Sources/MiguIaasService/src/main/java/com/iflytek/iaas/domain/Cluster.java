package com.iflytek.iaas.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * 〈集群实体〉
 *
 * @author ruizhao3
 * @create 2018/4/10
 */
@Entity
@Table(name="cluster")
public class Cluster implements Serializable{
    private static final long serialVersionUID = -5352050506682810176L;

    private Integer id;
    private String name;
    private String annotation;
    private String creator;
    private boolean valid;
    private Date createtime;

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
    @Column(name = "createtime")
    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cluster cluster = (Cluster) o;
        return id.equals(cluster.id) &&
                valid == cluster.valid &&
                Objects.equals(name, cluster.name) &&
                Objects.equals(annotation, cluster.annotation) &&
                Objects.equals(creator, cluster.creator) &&
                Objects.equals(createtime, cluster.createtime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, name, annotation, creator, valid, createtime);
    }
}

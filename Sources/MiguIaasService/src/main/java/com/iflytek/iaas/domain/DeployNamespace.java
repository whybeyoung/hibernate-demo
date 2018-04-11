package com.iflytek.iaas.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "deploy_namespace", schema = "migu_iaas", catalog = "")
public class DeployNamespace {
    private String ns;
    private Timestamp createtime;

    @Id
    @Column(name = "ns")
    public String getNs() {
        return ns;
    }

    public void setNs(String ns) {
        this.ns = ns;
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
        DeployNamespace that = (DeployNamespace) o;
        return Objects.equals(ns, that.ns) &&
                Objects.equals(createtime, that.createtime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(ns, createtime);
    }
}
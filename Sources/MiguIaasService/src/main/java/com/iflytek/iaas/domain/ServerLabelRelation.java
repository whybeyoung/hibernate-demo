package com.iflytek.iaas.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "server_label_relation", schema = "migu_iaas", catalog = "")
@IdClass(ServerLabelRelationPK.class)
public class ServerLabelRelation {
    private int serverId;
    private int labelId;
    private Timestamp createtime;

    @Id
    @Column(name = "server_id")
    public int getServerId() {
        return serverId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }

    @Id
    @Column(name = "label_id")
    public int getLabelId() {
        return labelId;
    }

    public void setLabelId(int labelId) {
        this.labelId = labelId;
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
        ServerLabelRelation that = (ServerLabelRelation) o;
        return serverId == that.serverId &&
                labelId == that.labelId &&
                Objects.equals(createtime, that.createtime);
    }

    @Override
    public int hashCode() {

        return Objects.hash(serverId, labelId, createtime);
    }
}

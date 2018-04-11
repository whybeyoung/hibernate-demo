package com.iflytek.iaas.domain;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Objects;

public class ServerLabelRelationPK implements Serializable {
    private int serverId;
    private int labelId;

    @Column(name = "server_id")
    @Id
    public int getServerId() {
        return serverId;
    }

    public void setServerId(int serverId) {
        this.serverId = serverId;
    }

    @Column(name = "label_id")
    @Id
    public int getLabelId() {
        return labelId;
    }

    public void setLabelId(int labelId) {
        this.labelId = labelId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServerLabelRelationPK that = (ServerLabelRelationPK) o;
        return serverId == that.serverId &&
                labelId == that.labelId;
    }

    @Override
    public int hashCode() {

        return Objects.hash(serverId, labelId);
    }
}

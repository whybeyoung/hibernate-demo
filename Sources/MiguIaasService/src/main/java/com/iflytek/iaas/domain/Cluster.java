package com.iflytek.iaas.domain;

import com.iflytek.iaas.dto.ClusterDTO;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
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

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    @Column(name = "id")
    private Integer id;
    private String name;
    private String annotation;
    private boolean valid;
    private Date createtime;

    @JoinColumn(name = "creator")
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

    @OneToMany(mappedBy = "cluster", fetch = FetchType.LAZY)
    private List<Server> servers;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "cluster_label_id")
    private ClusterLabel clusterLabel;

    public ClusterDTO toClusterDTO() {
        ClusterDTO clusterDTO = new ClusterDTO();
        BeanUtils.copyProperties(this, clusterDTO);
        return clusterDTO;
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

    
    public boolean getValid() {
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Server> getServers() {
        return servers;
    }

    public void setServers(List<Server> servers) {
        this.servers = servers;
    }

    public ClusterLabel getClusterLabel() {
        return clusterLabel;
    }

    public void setClusterLabel(ClusterLabel clusterLabel) {
        this.clusterLabel = clusterLabel;
    }
}

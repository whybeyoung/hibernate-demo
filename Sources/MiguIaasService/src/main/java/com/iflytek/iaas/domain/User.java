/**
 * Copyright (C), 科大讯飞股份有限公司
 * FileName: User
 * Author:   xwliu
 * Date:     2018/4/1 18:12
 * Description: 用户实体
 */
package com.iflytek.iaas.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 〈用户实体〉
 *
 * @author xwliu
 * @create 2018/4/1
 */
@Entity
@Table(name="user")
public class User implements Serializable {

    private static final long serialVersionUID = -8623297218231202832L;

    @Id
    private String id;

    @Column
    private String account;

    @Column
    private String nickname;

    @Column
    private String salt;

    @Column
    @JsonIgnore
    private String password;

    @Column
    private String email;

    @Column
    private String phone;

    @Column(name="role_id")
    private String roleId;

    @Column(name="department_code")
    private String departmentCode;

    @Column
    private boolean valid;

    @OneToMany(cascade=CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="creator")
    private List<Cluster> clusters;

    @Column
    private Date createtime;

    @Column
    private Date updatetime;

    public void addCluster(Cluster cluster) {
        if(cluster != null) {
            if(clusters == null){
                clusters = new ArrayList<Cluster>();
            }
            clusters.add(cluster);
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleCode) {
        this.roleId = roleCode;
    }

    public String getDepartmentCode() {
        return departmentCode;
    }

    public void setDepartmentCode(String departmentCode) {
        this.departmentCode = departmentCode;
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

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public List<Cluster> getClusters() {
        return clusters;
    }

    public void setClusters(List<Cluster> clusters) {
        this.clusters = clusters;
    }
}

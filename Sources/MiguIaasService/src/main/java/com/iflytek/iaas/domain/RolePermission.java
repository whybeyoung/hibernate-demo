/**
 * Copyright (C), 科大讯飞股份有限公司
 * FileName: RolePermission
 * Author:   xwliu
 * Date:     2018/4/2 15:37
 * Description: 角色权限关联实体
 */
package com.iflytek.iaas.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 〈角色权限关联实体〉
 *
 * @author xwliu
 * @create 2018/4/2
 */
@Entity
@Table(name="user_role_permission")
public class RolePermission implements Serializable {

    private static final long serialVersionUID = -4000683092320385447L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="role_id")
    private String roleId;

    @Column(name="permission_code")
    private String permissionCode;

    @Column
    private Date createtime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleCode) {
        this.roleId = roleCode;
    }

    public String getPermissionCode() {
        return permissionCode;
    }

    public void setPermissionCode(String permissionCode) {
        this.permissionCode = permissionCode;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}

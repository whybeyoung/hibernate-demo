/**
 * Copyright (C), 科大讯飞股份有限公司
 * FileName: Permission
 * Author:   xwliu
 * Date:     2018/4/2 15:31
 * Description: 权限实体
 */
package com.iflytek.iaas.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 〈权限实体〉
 *
 * @author xwliu
 * @create 2018/4/2
 */
@Entity
@Table(name="user_permission")

public class Permission implements Serializable {

    private static final long serialVersionUID = -631590045288680417L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private Integer pid;

    @Column
    private String code;

    @Column
    private String name;

    @Column
    private String annotation;

    @Column
    private Date createtime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}

/**
 * Copyright (C), 科大讯飞股份有限公司
 * FileName: Role
 * Author:   xwliu
 * Date:     2018/4/2 15:21
 * Description: 角色实体
 */
package com.iflytek.iaas.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 〈角色实体〉
 *
 * @author xwliu
 * @create 2018/4/2
 */
@Entity
@Table(name="user_role")
public class Role implements Serializable{

    private static final long serialVersionUID = 8324476470215492474L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;

    @Column
    private String annotation;

    @Column
    private String createtime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }
}

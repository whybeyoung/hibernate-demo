/**
 * Copyright (C), 科大讯飞股份有限公司
 * FileName: OperationLog
 */
package com.iflytek.iaas.domain;

import com.iflytek.iaas.consts.LogType;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 〈操作日志表〉
 *
 * @author xwliu
 * @create 2018/4/18
 */
@Entity
@Table(name="operation_log")
public class OperationLog implements Serializable {

    private static final long serialVersionUID = -7770495026961423616L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private Integer type;

    @Column
    private String detail;

    @Column
    private String obj;

    @Column
    private String param;

    @Column
    private String creator;

    @Column
    private Date createtime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getObj() {
        return obj;
    }

    public void setObj(String obj) {
        this.obj = obj;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }
}

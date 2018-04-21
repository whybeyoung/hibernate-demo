/**
 * Copyright (C), 科大讯飞股份有限公司
 * FileName: OperationLogDTO
 */
package com.iflytek.iaas.dto;

import com.iflytek.iaas.consts.LogType;

import java.util.Date;

/**
 * 〈操作日志DTO〉
 *
 * @author xwliu
 * @create 2018/4/18
 */
public class OperationLogDTO {

    private LogType type;
    private String detail;
    private String obj;
    private String param;
    private String creator;
    private Date createtime;

    public OperationLogDTO() {
    }

    public OperationLogDTO(LogType type, String detail, String obj, String param, String creator) {
        this.type = type;
        this.detail = detail;
        this.obj = obj;
        this.param = param;
        this.creator = creator;
    }

    public LogType getType() {
        return type;
    }

    public void setType(LogType type) {
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

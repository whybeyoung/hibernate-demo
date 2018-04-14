/**
 * Copyright (C), 科大讯飞股份有限公司
 * FileName: EnvDTO
 */
package com.iflytek.iaas.dto.k8s;

/**
 * 〈环境变量DTO〉
 *
 * @author xwliu
 * @create 2018/4/13
 */
public class EnvDTO {
    private String key;
    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}

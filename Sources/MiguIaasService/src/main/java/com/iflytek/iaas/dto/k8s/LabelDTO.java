/**
 * Copyright (C), 科大讯飞股份有限公司
 * FileName: LabelDTO
 */
package com.iflytek.iaas.dto.k8s;

/**
 * 〈标签dto〉
 *
 * @author xwliu
 * @create 2018/4/12
 */
public class LabelDTO {

    private String key;
    private String value;

    public LabelDTO() {

    }

    public LabelDTO(String key, String value) {
        this.key = key;
        this.value = value;
    }

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

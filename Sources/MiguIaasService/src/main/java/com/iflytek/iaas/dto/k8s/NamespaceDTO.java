/**
 * Copyright (C), 科大讯飞股份有限公司
 * FileName: NamespaceDTO
 */
package com.iflytek.iaas.dto.k8s;

/**
 * 〈命名空间〉
 *
 * @author xwliu
 * @create 2018/4/11
 */
public class NamespaceDTO {
    public String getNameSpace() {
        return nameSpace;
    }

    public void setNameSpace(String nameSpace) {
        this.nameSpace = nameSpace;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    private String nameSpace;
    private String uid;

}

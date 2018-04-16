/**
 * Copyright (C), 科大讯飞股份有限公司
 * FileName: MountVolumeDTO
 */
package com.iflytek.iaas.dto.k8s;

import com.iflytek.iaas.utils.ToolUtils;

import java.util.Date;

/**
 * 〈挂载目录配置DTO〉
 *
 * @author xwliu
 * @create 2018/4/13
 */
public class MountVolumeDTO {

    private String serverDir;
    private String containerDir;
    private String name;

    public String getServerDir() {
        return serverDir;
    }

    public void setServerDir(String serverDir) {
        this.serverDir = serverDir;
    }

    public String getContainerDir() {
        return containerDir;
    }

    public void setContainerDir(String containerDir) {
        this.containerDir = containerDir;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

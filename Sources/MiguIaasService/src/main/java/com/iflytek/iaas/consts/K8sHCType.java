package com.iflytek.iaas.consts;

/**
 * k8s健康检查类型
 * Created by ruizhao3 on 2018/4/17.
 */
public enum K8sHCType {
    /**
     *shell script or command
     */
    ExecAction,
    /**
     * use tcp, bind a port
     */
    TCPSocketAction,
    /**
     * need give a http-address, elements: hostname, port, url, header etc.
     */
    HTTPGetAction
}

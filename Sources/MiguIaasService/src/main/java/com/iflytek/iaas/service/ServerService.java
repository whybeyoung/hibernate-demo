/**
 * Copyright (C), 科大讯飞股份有限公司
 * FileName: ServerService
 * Author:   xwliu
 * Date:     2018/4/2 15:09
 * Description: 主机服务
 */
package com.iflytek.iaas.service;


import com.iflytek.iaas.domain.Server;
import com.iflytek.iaas.dto.k8s.ServerInfoDTO;
import io.kubernetes.client.ApiException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 〈主机服务接口〉
 *
 * @author xwliu
 * @create 2018/4/2
 */
public interface ServerService {

    public Map serverStatus(List<String> hostNames, long start, long end, int step);

    public void deleteServerLabel(Server server) throws IOException, ApiException;

    public void addServerLabel(Server server, String labelName, Integer clusterId) throws IOException, ApiException;

    public List<ServerInfoDTO>  index(String from, String hostname, String os, String clusterName, String ipv4) throws IOException, ApiException;

    public Optional<Server> show(Integer id) throws IOException, ApiException;

    public Map serverStatus(Integer id);

    public Server create(Server serverInfoDTO);

    public ServerInfoDTO update(Integer id, ServerInfoDTO si);

    public long count();
}

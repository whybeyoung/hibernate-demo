/**
 * Copyright (C), 科大讯飞股份有限公司
 * FileName: ServerController
 * Author:   xwliu
 * Date:     2018/4/2 15:01
 * Description: 主机相关接口
 */
package com.iflytek.iaas.controller;

import com.iflytek.iaas.dao.ServerDao;
import com.iflytek.iaas.domain.Server;
import com.iflytek.iaas.dto.k8s.ServerInfoDTO;
import com.iflytek.iaas.service.K8SService;
import com.iflytek.iaas.service.ServerService;
import io.kubernetes.client.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 〈主机相关接口〉
 *
 * @author xwliu
 * @create 2018/4/2
 */
@RestController
@RequestMapping(path="api/v1")
public class ServerController {

    @Autowired
    private K8SService k8SService;

    @Autowired
    private ServerService serverService;

    @GetMapping("/servers")
    public List<ServerInfoDTO>  index(String from, String hostname, String os, String clusterName, String ipv4) throws IOException, ApiException {
        return serverService.index(from, hostname, os, clusterName, ipv4);
    }

    @GetMapping("/servers/{id}")
    public Optional<Server> show(@PathVariable Integer id) throws IOException, ApiException {
        return serverService.show(id);
    }

    @GetMapping("/servers/{id}/status")
    public Map serverStatus(@PathVariable Integer id) {
        return serverService.serverStatus(id);
    }

    @PostMapping("/servers")
    public Server create(@RequestBody ServerInfoDTO serverInfoDTO) {
        Server server = serverInfoDTO.toServer();
        server.setValid(true);
        return serverService.create(server);
    }

    @PatchMapping("/servers/{id}")
    public ServerInfoDTO update(@PathVariable Integer id, @RequestBody ServerInfoDTO si) {
        return serverService.update(id, si);
    }

    @GetMapping("/servers/count")
    public long count() {
        return serverService.count();
    }

}

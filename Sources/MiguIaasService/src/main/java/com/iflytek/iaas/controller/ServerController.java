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
import io.kubernetes.client.ApiException;
import org.apache.catalina.util.ServerInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
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
    private ServerDao serverDao;


    @GetMapping("/servers")
    public List<ServerInfoDTO>  index(String hostname, String from) throws IOException, ApiException {
        if("k8s-local".equals(from)) {
            List<ServerInfoDTO> k8sServerInfoDTOs = k8SService.getOnlineServerNodes();
            List<ServerInfoDTO> localServerInfoDTOs = serverDao.findAll().stream().map(i -> i.toServerInfoDTO()).collect(Collectors.toList());
            k8sServerInfoDTOs.removeAll(localServerInfoDTOs);
            return k8sServerInfoDTOs;
        } else if("local".equals(from)) {
            return serverDao.findAll().stream().map(i -> i.toServerInfoDTO()).collect(Collectors.toList());
        } else if("localunadded".equals(from)) {
            List<Server> localunaddedServers = serverDao.findByClusterIdIsNull();
            return localunaddedServers.stream().map(i -> i.toServerInfoDTO()).collect(Collectors.toList());
        }
        return null;
    }

    @GetMapping("/servers/{hostname}")
    public ServerInfoDTO show(@PathVariable String hostname) throws IOException, ApiException {
        return k8SService.getServerInfoByName(hostname);
    }

    @PostMapping("/servers")
    public ServerInfoDTO create(@RequestBody ServerInfoDTO serverInfoDTO) {
        Server server = serverInfoDTO.toServer();
        server = serverDao.save(server);
        return server.toServerInfoDTO();
    }

}

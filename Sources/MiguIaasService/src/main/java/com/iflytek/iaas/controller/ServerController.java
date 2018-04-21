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
    private ServerDao serverDao;

    @Autowired
    private ServerService serverService;

    @GetMapping("/servers")
    public List<ServerInfoDTO>  index(String from, String hostname, String os, String clusterName, String ipv4) throws IOException, ApiException {

        if("k8s-local".equals(from)) {
            List<ServerInfoDTO> k8sServerInfoDTOs = new ArrayList<ServerInfoDTO>();
            if(!StringUtils.isEmpty(hostname)) {
                //查询未添加到本地数据库中的主机
                Server localServer = serverDao.findByHostname(hostname);
                if(localServer != null) {
                    return new ArrayList<>();
                } else {
                    ServerInfoDTO serverInfoDTO = k8SService.getServerInfoByHostname(hostname);
                    if(serverInfoDTO != null) {
                        k8sServerInfoDTOs.add(serverInfoDTO);
                    }
                }
            } else {
                k8sServerInfoDTOs = k8SService.getAllServerNodes();
                List<ServerInfoDTO> localServerInfoDTOs = serverDao.findAll().stream().map(i -> i.toServerInfoDTO()).collect(Collectors.toList());
                k8sServerInfoDTOs.removeAll(localServerInfoDTOs);
            }
            return k8sServerInfoDTOs;
        } else if("local".equals(from)) {
            ipv4 = convertLikeValue(ipv4 );
            hostname = convertLikeValue(hostname);
            os = convertLikeValue(os);
            List<Server> servers = serverDao.findAllByIpv4LikeAndHostnameLikeAndOsLike(ipv4, hostname, os);
            return servers.stream().map(i -> i.toServerInfoDTO()).collect(Collectors.toList());
        } else if("localunadded".equals(from)) {
            ipv4 = convertLikeValue(ipv4 );
            hostname = convertLikeValue(hostname);
            os = convertLikeValue(os);
            List<Server> localunaddedServers = serverDao.findByClusterIdIsNullAndIpv4LikeAndHostnameLike(ipv4, hostname);
            return localunaddedServers.stream().map(i -> i.toServerInfoDTO()).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    @GetMapping("/servers/{id}")
    public Optional<Server> show(@PathVariable Integer id) throws IOException, ApiException {
        Optional<Server> server = serverDao.findById(id);
        return server;
    }

    @GetMapping("/servers/{id}/status")
    public Map serverStatus(@PathVariable Integer id) {
        Optional<Server> server = serverDao.findById(id);
        long now = System.currentTimeMillis();
        long start = now - 1000*60*60*6;
        List<String> hostnames = new ArrayList<>();
        hostnames.add(server.get().getHostname());
        return serverService.serverStatus(hostnames, start, now, 60*30);
    }

    @PostMapping("/servers")
    public ServerInfoDTO create(@RequestBody ServerInfoDTO serverInfoDTO) {
        Server server = serverInfoDTO.toServer();
        server.setValid(true);
        server = serverDao.save(server);
        return server.toServerInfoDTO();
    }

    @PatchMapping("/servers/{id}")
    public ServerInfoDTO update(@PathVariable Integer id, @RequestBody ServerInfoDTO si) {
        Optional<Server> s = serverDao.findById(id);
        s.get().setValid(si.getValid());
        return serverDao.save(s.get()).toServerInfoDTO();
    }

    @GetMapping("/servers/count")
    public long count() {
        return serverDao.count();
    }

    private String convertLikeValue(String value) {
        if(StringUtils.isEmpty(value)) {
            return "%";
        } else {
            return "%" + value + "%";
        }
    }

}

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
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
    public List<ServerInfoDTO>  index(String from, String hostname, String os, String clusterName, String ipv4) throws IOException, ApiException {
        if("k8s-local".equals(from)) {
            List<ServerInfoDTO> k8sServerInfoDTOs = new ArrayList<ServerInfoDTO>();
            if(hostname != null) {
                //查询未添加到本地数据库中的主机
                Server localServer = serverDao.findByHostname(hostname);
                if(localServer != null) {
                    return new ArrayList<>();
                } else {
                    ServerInfoDTO serverInfoDTO = k8SService.getServerInfoByHostname(hostname);
                    k8sServerInfoDTOs.add(serverInfoDTO);
                }
            } else {
                k8sServerInfoDTOs = k8SService.getAllServerNodes();
                List<ServerInfoDTO> localServerInfoDTOs = serverDao.findAll().stream().map(i -> i.toServerInfoDTO()).collect(Collectors.toList());
                k8sServerInfoDTOs.removeAll(localServerInfoDTOs);
            }
            return k8sServerInfoDTOs;
        } else if("local".equals(from)) {
            List<Server> servers = serverDao.findAllByIpv4LikeAndHostnameLikeAndOsLike(convertLikeValue(ipv4 ), convertLikeValue(hostname), convertLikeValue(os));
            return servers.stream().map(i -> i.toServerInfoDTO()).collect(Collectors.toList());
        } else if("localunadded".equals(from)) {
            List<Server> localunaddedServers = serverDao.findByClusterIdIsNull();
            return localunaddedServers.stream().map(i -> i.toServerInfoDTO()).collect(Collectors.toList());
        }
        return new ArrayList<>();
    }

    @GetMapping("/servers/{hostname}")
    public ServerInfoDTO show(@PathVariable String hostname) throws IOException, ApiException {
        return k8SService.getServerInfoByHostname(hostname);
    }

    @PostMapping("/servers")
    public ServerInfoDTO create(@RequestBody ServerInfoDTO serverInfoDTO) {
        Server server = serverInfoDTO.toServer();
        server = serverDao.save(server);
        return server.toServerInfoDTO();
    }

    private String convertLikeValue(String value) {
        if(StringUtils.isEmpty(value)) {
            return "%";
        } else {
            return "%" + value + "%";
        }
    }

}

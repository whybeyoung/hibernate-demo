package com.iflytek.iaas.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.iflytek.iaas.dao.ClusterLabelDao;
import com.iflytek.iaas.dao.ServerDao;
import com.iflytek.iaas.domain.ClusterLabel;
import com.iflytek.iaas.domain.Server;
import com.iflytek.iaas.dto.k8s.LabelDTO;
import com.iflytek.iaas.dto.k8s.NetworkFlowDTO;
import com.iflytek.iaas.dto.k8s.ServerInfoDTO;
import com.iflytek.iaas.service.K8SService;
import com.iflytek.iaas.service.ServerService;
import io.kubernetes.client.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


@Service("ServerService")
public class ServerServiceImpl implements ServerService {

    @Autowired
    private ClusterLabelDao clusterLabelDao;
    @Autowired
    private K8SService k8SService;
    @Autowired
    private ServerDao serverDao;

    @Override
    public void deleteServerLabel(Server server) throws IOException, ApiException {
        List<LabelDTO> labels = new ArrayList<>();
        LabelDTO labelDTO = new LabelDTO();
        ClusterLabel cl = clusterLabelDao.findOneByClusterId(server.getCluster().getId());
        labelDTO.setKey(cl.getKey());
        labelDTO.setValue(cl.getValue());
        labels.add(labelDTO);
        k8SService.deleteServerLabel(server.getHostname(), labels);
    }

    @Override
    public void addServerLabel(Server server, String labelName, Integer clusterId) throws IOException, ApiException {
        
    }

    @Override
    public List<ServerInfoDTO> index(String from, String hostname, String os, String clusterName, String ipv4) throws IOException, ApiException {
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

    @Override
    public Optional<Server> show(Integer id) throws IOException, ApiException {
        return serverDao.findById(id);
    }

    @Override
    public Map serverStatus(Integer id) {
        Optional<Server> server = serverDao.findById(id);
        long now = System.currentTimeMillis();
        long start = now - 1000*60*60*6;
        List<String> hostnames = new ArrayList<>();
        hostnames.add(server.get().getHostname());
        return serverStatus(hostnames, start, now, 60*30);
    }

    @Override
    public Server create(Server server) {
        server = serverDao.save(server);
        return server;
    }

    @Override
    public ServerInfoDTO update(Integer id, ServerInfoDTO si) {
        Optional<Server> s = serverDao.findById(id);
        s.get().setValid(si.getValid());
        return serverDao.save(s.get()).toServerInfoDTO();
    }

    @Override
    public long count() {
        return serverDao.count();
    }

    @Override
    public Map serverStatus(List<String> hostNames, long start, long end, int step) {
        Map status = new HashMap();
        JSONArray cpuStatus = k8SService.getServerCPUUsageRateByHostname(hostNames, start, end, step);
        JSONArray memoryStatus = k8SService.getServerMemoryUsageRateByHostname(hostNames, start, end, step);
        NetworkFlowDTO networkStatus = k8SService.getServerNetworkUsageRateByHostname(hostNames, start, end, step);
        status.put("cpu", cpuStatus);
        status.put("memory", memoryStatus);
        status.put("network", networkStatus);
        return status;
    }

    private String convertLikeValue(String value) {
        if(StringUtils.isEmpty(value)) {
            return "%";
        } else {
            return "%" + value + "%";
        }
    }
}

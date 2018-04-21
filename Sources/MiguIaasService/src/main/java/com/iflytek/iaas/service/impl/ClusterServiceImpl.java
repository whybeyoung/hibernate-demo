/**
 * Copyright (C), 科大讯飞股份有限公司
 * FileName: UserServiceImpl
 */
package com.iflytek.iaas.service.impl;

import com.iflytek.iaas.dao.ClusterDao;
import com.iflytek.iaas.dao.ClusterLabelDao;
import com.iflytek.iaas.dao.ServerDao;
import com.iflytek.iaas.dao.UserDao;
import com.iflytek.iaas.domain.Cluster;
import com.iflytek.iaas.domain.ClusterLabel;
import com.iflytek.iaas.domain.Server;
import com.iflytek.iaas.domain.User;
import com.iflytek.iaas.dto.ClusterDTO;
import com.iflytek.iaas.dto.k8s.LabelDTO;
import com.iflytek.iaas.dto.k8s.PodDTO;
import com.iflytek.iaas.service.ClusterService;
import com.iflytek.iaas.service.K8SService;
import com.iflytek.iaas.service.UserService;
import io.kubernetes.client.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 〈集群服务〉
 *
 * @author ruizhao3
 * @create 2018/4/10
 */
@Service("ClusterService")
public class ClusterServiceImpl implements ClusterService {
    private Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private ClusterDao clusterDao;

    @Autowired
    private ServerDao serverDao;

    @Autowired
    private ClusterLabelDao clusterLabelDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private K8SService k8SService;

    public List<ClusterDTO> index() {
        List<Cluster> clusters = clusterDao.findAll();
        List<ClusterDTO> clusterDTOs = clusters.stream().map(c -> {
            ClusterDTO clusterDTO = c.toClusterDTO();
            List<Server> servers = serverDao.findByClusterId(c.getId());
            clusterDTO.setServers(servers);
            setUsageWithStep(clusterDTO, servers, System.currentTimeMillis(), System.currentTimeMillis(), 60);


            ClusterLabel cl = clusterLabelDao.findOneByClusterId(c.getId());
            LabelDTO labelDTO = new LabelDTO(cl.getKey(), cl.getValue());
            try {
                List<PodDTO> pods = k8SService.getPodsByCluster(labelDTO, hostnames(servers));
                clusterDTO.setPodsNum(pods.size());
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ApiException e) {
                e.printStackTrace();
            }
            return  clusterDTO;
        }).collect(Collectors.toList());

        return clusterDTOs;
    }


    public Cluster create(ClusterDTO clusterDTO, User user) {
        Cluster cluster = clusterDTO.toCluster();

        cluster.setUser(user);
        List<Server> servers = clusterDTO.getServers();
        cluster.setServers(servers);
        ClusterLabel clusterLabel = new ClusterLabel(clusterDTO.getLabelName(), clusterDTO.getLabelName());
        cluster.setClusterLabel(clusterLabel);
        return clusterDao.save(cluster);
    }

    @Override
    public Integer getClusterSrvCount(Integer clusterId) {
        return serverDao.countByClusterIdAndStatus(clusterId, true);
    }

    public Cluster update(ClusterDTO clusterDTO) {
        Cluster cluster = clusterDTO.toCluster();
        cluster.setServers(clusterDTO.getServers());
        cluster.setClusterLabel(new ClusterLabel(clusterDTO.getLabelName(), clusterDTO.getLabelName()));
        return clusterDao.save(cluster);
    }

    public ClusterDTO show(Integer id) {
        Optional<Cluster> cluster = clusterDao.findById(id);
        ClusterDTO clusterDTO = cluster.get().toClusterDTO();
        List<Server> servers = serverDao.findByClusterId(cluster.get().getId());
        clusterDTO.setServers(servers);
        clusterDTO.setLabelName(clusterLabelDao.findOneByClusterId(id).getValue());

        setUsage(clusterDTO, servers);
        return clusterDTO;
    }

    public void remove(Integer id) {

        List<Server> servers = serverDao.findByClusterId(id);
        for(Server s : servers) {
            s.setCluster(null);
            serverDao.save(s);
        }
        ClusterLabel cl = clusterLabelDao.findOneByClusterId(id);
        clusterLabelDao.delete(cl);

        clusterDao.deleteById(id);
    }

    private void setUsage(ClusterDTO clusterDTO, List<Server> servers) {
        long end = System.currentTimeMillis();
        long start = end - 1000*60*60*6;
        setUsageWithStep(clusterDTO, servers, start, end, 60*30);
    }

    private void setUsageWithStep(ClusterDTO clusterDTO, List<Server> servers, long start, long end, int step) {
        List<String> hostnames = hostnames(servers);
        clusterDTO.setCpuUsage(k8SService.getServerCPUUsageRateByHostname(hostnames, start, end, step));
        clusterDTO.setMemoryUsage(k8SService.getServerMemoryUsageRateByHostname(hostnames, start, end, step));
        clusterDTO.setNetworkUsage(k8SService.getServerNetworkUsageRateByHostname(hostnames, start, end, step));
    }

    private List<String> hostnames(List<Server> servers) {
        return servers.stream().map(s -> s.getHostname()).collect(Collectors.toList());
    }

}

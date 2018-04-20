/**
 * Copyright (C), 科大讯飞股份有限公司
 * FileName: ClusterController
 * Author:   xwliu
 * Date:     2018/4/2 15:00
 * Description: 集群相关controller
 */
package com.iflytek.iaas.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
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
import com.iflytek.iaas.dto.k8s.NetworkFlowDTO;
import com.iflytek.iaas.dto.k8s.PodDTO;
import com.iflytek.iaas.service.ClusterService;
import com.iflytek.iaas.service.K8SService;
import io.kubernetes.client.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 〈集群相关controller〉
 *
 * @author xwliu
 * @create 2018/4/2
 */
@RestController
@RequestMapping(path="api/v1")
public class ClusterController {

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

    @Autowired
    private ClusterService clusterService;

    @GetMapping("/clusters")
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

    @PostMapping("/clusters")
    public Cluster create(HttpServletRequest request, @RequestBody ClusterDTO clusterDTO) {
        User user = (User) request.getSession().getAttribute("CURRENT_USER");

        Cluster cluster = clusterDTO.toCluster();
        cluster.setCreator(user.getId());
        cluster = clusterDao.save(cluster);

        List<Server> servers = clusterDTO.getServers();
        for(Server s : servers) {
            s.setClusterId(cluster.getId());
            s = serverDao.save(s);
        }

        ClusterLabel clusterLabel = new ClusterLabel(clusterDTO.getLabelName(), clusterDTO.getLabelName(), cluster.getId());
        clusterLabelDao.save(clusterLabel);

        return cluster;
    }

    @PatchMapping("/clusters")
    public Cluster update(HttpServletRequest request, @RequestBody ClusterDTO clusterDTO) {
        Cluster cluster = clusterDTO.toCluster();
        cluster = clusterDao.save(cluster);

        List<Server> servers = clusterDTO.getServers();
        for(Server s : servers) {
            s.setClusterId(cluster.getId());
            s = serverDao.save(s);
        }

        ClusterLabel clusterLabel = clusterLabelDao.findOneByClusterId(cluster.getId());
        clusterLabel.setValue(clusterDTO.getLabelName());
        clusterLabelDao.save(clusterLabel);

        return cluster;
    }

    @GetMapping("/clusters/{id}")
    public ClusterDTO show(@PathVariable Integer id) {
        Optional<Cluster> cluster = clusterDao.findById(id);
        ClusterDTO clusterDTO = cluster.get().toClusterDTO();
        List<Server> servers = serverDao.findByClusterId(cluster.get().getId());
        clusterDTO.setServers(servers);
        clusterDTO.setLabelName(clusterLabelDao.findOneByClusterId(id).getValue());

        Optional<User> createUser = userDao.findById(clusterDTO.getCreator());
        clusterDTO.setCreatorName(createUser.get().getNickname());

        setUsage(clusterDTO, servers);
        return clusterDTO;
    }

    @GetMapping("/clusters/{id}/srvcount")
    public Integer clusterServerCount(@PathVariable Integer id) {
        return clusterService.getClusterSrvCount(id);
    }

    @DeleteMapping("clusters/{id}")
    public void remove(@PathVariable Integer id) {

        List<Server> servers = serverDao.findByClusterId(id);
        for(Server s : servers) {
            s.setClusterId(null);
            serverDao.save(s);
        }
        clusterLabelDao.deleteAllByClusterId(id);

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

/**
 * Copyright (C), 科大讯飞股份有限公司
 * FileName: ClusterController
 * Author:   xwliu
 * Date:     2018/4/2 15:00
 * Description: 集群相关controller
 */
package com.iflytek.iaas.controller;

import com.iflytek.iaas.dao.ClusterDao;
import com.iflytek.iaas.dao.ClusterLabelDao;
import com.iflytek.iaas.dao.ServerDao;
import com.iflytek.iaas.domain.Cluster;
import com.iflytek.iaas.domain.ClusterLabel;
import com.iflytek.iaas.domain.Server;
import com.iflytek.iaas.domain.User;
import com.iflytek.iaas.dto.ClusterDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;

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

    @GetMapping("/clusters")
    public List<Cluster> index() {
        return clusterDao.findAll();
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
        clusterDTO.setServers(serverDao.findByClusterId(cluster.get().getId()));
        clusterDTO.setLabelName(clusterLabelDao.findOneByClusterId(id).getValue());
        return clusterDTO;
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

}

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
import com.iflytek.iaas.domain.Cluster;
import com.iflytek.iaas.domain.ClusterLabel;
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

        ClusterLabel clusterLabel = new ClusterLabel(clusterDTO.getLabelName(), clusterDTO.getLabelName(), cluster.getId());
        clusterLabelDao.save(clusterLabel);

        return cluster;
    }

    @GetMapping("/clusters/{id}")
    public Optional<Cluster> show(@PathVariable Integer id) {
        return clusterDao.findById(id);
    }

    @DeleteMapping("clusters/{id}")
    public void remove(@PathVariable Integer id) {
        clusterDao.deleteById(id);
    }

}

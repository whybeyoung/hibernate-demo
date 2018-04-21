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
    private ClusterService clusterService;

    @Autowired
    private K8SService k8SService;

    @Autowired
    private ClusterService clusterService;

    @GetMapping("/clusters")
    public List<ClusterDTO> index() {
        return clusterService.index();
    }

    @PostMapping("/clusters")
    public Cluster create(HttpServletRequest request, @RequestBody ClusterDTO clusterDTO) {
        User user = (User) request.getSession().getAttribute("CURRENT_USER");
        return clusterService.create(clusterDTO, user);
    }

    @PatchMapping("/clusters")
    public Cluster update(@RequestBody ClusterDTO clusterDTO) {
        return clusterService.update(clusterDTO);
    }

    @GetMapping("/clusters/{id}")
    public ClusterDTO show(@PathVariable Integer id) {
        return clusterService.show(id);
    }

    @GetMapping("/clusters/{id}/srvcount")
    public Integer clusterServerCount(@PathVariable Integer id) {
        return clusterService.getClusterSrvCount(id);
    }

    @DeleteMapping("clusters/{id}")
    public void remove(@PathVariable Integer id) {
        clusterService.remove(id);
    }

}

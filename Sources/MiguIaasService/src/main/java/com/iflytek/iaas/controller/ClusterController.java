/**
 * Copyright (C), 科大讯飞股份有限公司
 * FileName: ClusterController
 * Author:   xwliu
 * Date:     2018/4/2 15:00
 * Description: 集群相关controller
 */
package com.iflytek.iaas.controller;

import com.iflytek.iaas.domain.Cluster;
import com.iflytek.iaas.domain.User;
import com.iflytek.iaas.dto.ClusterDTO;
import com.iflytek.iaas.service.ClusterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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

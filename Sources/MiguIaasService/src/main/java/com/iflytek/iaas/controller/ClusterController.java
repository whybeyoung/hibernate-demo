/**
 * Copyright (C), 科大讯飞股份有限公司
 * FileName: ClusterController
 * Author:   xwliu
 * Date:     2018/4/2 15:00
 * Description: 集群相关controller
 */
package com.iflytek.iaas.controller;

import com.iflytek.iaas.dao.ClusterDao;
import com.iflytek.iaas.dao.UserDao;
import com.iflytek.iaas.domain.Cluster;
import com.iflytek.iaas.domain.User;
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
    private ClusterDao clusterDao;

    @Autowired
    private UserDao userDao;

    @GetMapping("/clusters")
    public List<Cluster> index() {
        return clusterDao.findAll();
    }

    @PostMapping("/clusters")
    public Cluster create(HttpServletRequest request, @RequestBody Cluster cluster) {
        User user = (User) request.getSession().getAttribute("CURRENT_USER");
        cluster.setCreator(user.getId());
        return clusterDao.save(cluster);
    }

}

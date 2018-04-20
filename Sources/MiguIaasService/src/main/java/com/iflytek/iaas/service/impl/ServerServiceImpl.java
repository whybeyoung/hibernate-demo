package com.iflytek.iaas.service.impl;

import com.iflytek.iaas.dao.ClusterLabelDao;
import com.iflytek.iaas.domain.ClusterLabel;
import com.iflytek.iaas.domain.Server;
import com.iflytek.iaas.dto.k8s.LabelDTO;
import com.iflytek.iaas.service.K8SService;
import com.iflytek.iaas.service.ServerService;
import io.kubernetes.client.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service("ServerService")
public class ServerServiceImpl implements ServerService {

    @Autowired
    private ClusterLabelDao clusterLabelDao;
    @Autowired
    private K8SService k8SService;

    @Override
    public void deleteServerLabel(Server server) throws IOException, ApiException {
        List<LabelDTO> labels = new ArrayList<>();
        LabelDTO labelDTO = new LabelDTO();
        ClusterLabel cl = clusterLabelDao.findOneByClusterId(server.getClusterId());
        labelDTO.setKey(cl.getKey());
        labelDTO.setValue(cl.getValue());
        labels.add(labelDTO);
        k8SService.deleteServerLabel(server.getHostname(), labels);
    }

    @Override
    public void addServerLabel(Server server, String labelName, Integer clusterId) throws IOException, ApiException {

        ClusterLabel cl = new ClusterLabel(labelName, labelName, clusterId);
        clusterLabelDao.save(cl);

        List<LabelDTO> labels = new ArrayList<>();
        LabelDTO labelDTO = new LabelDTO();
        labelDTO.setKey(cl.getKey());
        labelDTO.setValue(cl.getValue());
        labels.add(labelDTO);
        k8SService.createServerLabel(server.getHostname(), labels);
    }
}

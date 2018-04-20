package com.iflytek.iaas.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.iflytek.iaas.dao.ClusterLabelDao;
import com.iflytek.iaas.domain.ClusterLabel;
import com.iflytek.iaas.domain.Server;
import com.iflytek.iaas.dto.k8s.LabelDTO;
import com.iflytek.iaas.dto.k8s.NetworkFlowDTO;
import com.iflytek.iaas.service.K8SService;
import com.iflytek.iaas.service.ServerService;
import io.kubernetes.client.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


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
}

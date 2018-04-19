/**
 * Copyright (C), 科大讯飞股份有限公司
 * FileName: k8sServiceImpl
 */
package com.iflytek.iaas.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.iflytek.iaas.consts.K8sAPPType;
import com.iflytek.iaas.dto.k8s.*;
import com.iflytek.iaas.service.K8SService;
import com.iflytek.iaas.utils.HttpClientUtil;
import io.kubernetes.client.ApiClient;
import io.kubernetes.client.ApiException;
import io.kubernetes.client.Configuration;
import io.kubernetes.client.apis.AppsV1beta1Api;
import io.kubernetes.client.apis.CoreV1Api;
import io.kubernetes.client.apis.ExtensionsV1beta1Api;
import io.kubernetes.client.custom.IntOrString;
import io.kubernetes.client.custom.Quantity;
import io.kubernetes.client.models.*;
import io.kubernetes.client.util.Config;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

/**
 * 〈k8s服务〉
 *
 * @author xwliu
 * @create 2018/4/11
 */
@Service("K8SService")
public class K8sServiceImpl  implements K8SService {
    private Logger logger = LoggerFactory.getLogger(K8SService.class);

    @Value("${prometheus.url}")
    private String prometheusUrl;

    @Override
    public Boolean createDeployNamespace(String namespace)throws IOException, ApiException{
        ApiClient client = Config.defaultClient();
        Configuration.setDefaultApiClient(client);
        CoreV1Api coreV1Api = new CoreV1Api();
        V1NamespaceList nsObj = coreV1Api.listNamespace("","","",false,"",0,"",5000,false);
        for(V1Namespace nsItem: nsObj.getItems()){
            if(nsItem.getMetadata().getName().equals(namespace) &&
                    nsItem.getStatus().getPhase().equalsIgnoreCase("Active")){
                return true;
            }
        }
        V1Namespace body = new V1Namespace();
        V1ObjectMeta meta = new V1ObjectMeta();
        meta.setName(namespace);
        body.setKind("Namespace");
        body.setMetadata(meta);
        try{
            V1Namespace ns = coreV1Api.createNamespace(body,null);
            return true;
        }catch (Exception e){
            logger.error(e.getMessage());
        }

        return false;
    }

    @Override
    public List<String> getNamespaces() throws IOException, ApiException{
        ApiClient client = Config.defaultClient();
        Configuration.setDefaultApiClient(client);
        CoreV1Api coreV1Api = new CoreV1Api();
        V1NamespaceList nsObj = coreV1Api.listNamespace(null,null,null,null,null,0,null,5000,null);
        List<String> nsList = new ArrayList<>();
        for(V1Namespace nsItem: nsObj.getItems()){
            nsList.add(nsItem.getMetadata().getName());
        }
        return nsList;
    }

    @Override
    public Boolean deleteNamespace(String namespace)throws IOException, ApiException {
        ApiClient client = Config.defaultClient();
        Configuration.setDefaultApiClient(client);
        CoreV1Api coreV1Api = new CoreV1Api();

        V1DeleteOptions options = new V1DeleteOptions();
        options.setGracePeriodSeconds((long)0);
        options.setPropagationPolicy("Background");

        try {
            coreV1Api.deleteNamespace(namespace, options, null, null, null, null);
            return true;
        }catch(Exception e){
            if(e.getMessage().contains("BEGIN_OBJECT")){
                return true;
            }
            logger.error(e.getMessage());
        }
        return false;
    }

    @Override
    public Boolean createImageDeployment(DeployConfigDTO deployConfigDTO)throws IOException, ApiException {
        ApiClient client = Config.defaultClient();
        Configuration.setDefaultApiClient(client);
        ExtensionsV1beta1Api  apiInstance = new ExtensionsV1beta1Api ();

        try{
            //部署标签
            Map<String,String> deployLabel = new HashMap<>();
            deployLabel.put(deployConfigDTO.getDeployLabel().getKey(),deployConfigDTO.getDeployLabel().getValue());

            V1ObjectMeta meta = new V1ObjectMeta();
            meta.setName(deployConfigDTO.getImgDeployName());
            meta.setNamespace(deployConfigDTO.getNamespace());
            meta.setLabels(deployLabel);

            //pod标签
            Map<String,String> podLabels = new HashMap<>();
            podLabels.put(deployConfigDTO.getDeployLabel().getKey(),deployConfigDTO.getDeployLabel().getValue());
            V1ObjectMeta podMeta = new V1ObjectMeta();
            podMeta.setLabels(podLabels);
            podMeta.setNamespace(deployConfigDTO.getNamespace());

            //服务器标签
            Map<String, String> serverLabels = new HashMap<>();
            serverLabels.put(deployConfigDTO.getServerLabel().getKey(),deployConfigDTO.getServerLabel().getValue());
            //服务器中待挂载的目录
            List<V1Volume> serverVolumes = new ArrayList<>();
            if(deployConfigDTO.getMountDirs() != null){
                for(MountVolumeDTO mountVolumeDTO:deployConfigDTO.getMountDirs()){
                    V1Volume volume = new V1Volume();
                    volume.setName(mountVolumeDTO.getName());
                    V1HostPathVolumeSource volumeSource = new V1HostPathVolumeSource();
                    volumeSource.setPath(mountVolumeDTO.getServerDir());
                    volumeSource.setType("DirectoryOrCreate");
                    volume.setHostPath(volumeSource);
                    serverVolumes.add(volume);
                }
            }

            //启动命令
            List<String> cmds = new ArrayList<>();
            if(StringUtils.isNotEmpty(deployConfigDTO.getInitCmd())){
                cmds.add(deployConfigDTO.getInitCmd());
            }

            //环境变量
            List<V1EnvVar> envs = new ArrayList<>();
            if(deployConfigDTO.getEnvs() != null){
                for(EnvDTO env: deployConfigDTO.getEnvs()){
                    V1EnvVar item = new V1EnvVar();
                    item.setName(env.getKey());
                    item.setValue(env.getValue());
                    envs.add(item);
                }
            }
            //健康检查,目前只支持命令行方式校验
            V1Probe probe = new V1Probe();
            V1ExecAction exec = new V1ExecAction();
            exec.setCommand(Arrays.asList(deployConfigDTO.getHealthCheckExec()));
            probe.setExec(exec);
            //容器中需要挂载的目录
            List<V1VolumeMount> containerVolumes = new ArrayList<>();
            if(deployConfigDTO.getMountDirs() != null){
                for(MountVolumeDTO mountVolumeDTO:deployConfigDTO.getMountDirs()){
                    V1VolumeMount volumeMount = new V1VolumeMount();
                    volumeMount.setName(mountVolumeDTO.getName());
                    volumeMount.setMountPath(mountVolumeDTO.getContainerDir());
                    containerVolumes.add(volumeMount);
                }
            }
            //资源限制
            V1ResourceRequirements resource = new V1ResourceRequirements();
            Map<String,Quantity> limit = new HashMap<>();
            if(deployConfigDTO.getCpuLimits() != null){
                Quantity cpuQuantity = new Quantity(new BigDecimal(deployConfigDTO.getCpuLimits()),Quantity.Format.DECIMAL_SI);
                limit.put("cpu",cpuQuantity);
            }
            if(deployConfigDTO.getMemoryLimits() != null){
                Quantity memoryQuantity = new Quantity(new BigDecimal(deployConfigDTO.getMemoryLimits()),Quantity.Format.BINARY_SI);
                limit.put("memory",memoryQuantity);
            }
            resource.setLimits(limit);

            List<V1Container> containers = new ArrayList<>();
            int podNum=1;
            if(deployConfigDTO.getPodContainers() != null){
                podNum = deployConfigDTO.getPodContainers();
            }
            for(int i=0;i <podNum;i++){
                V1Container container = new V1Container();
                container.setCommand(cmds);
                container.setImage(deployConfigDTO.getImgPath());
                container.setName(deployConfigDTO.getImgDeployName()+"-"+Integer.toString(i));
                container.setEnv(envs);
                if(deployConfigDTO.getContainerPort() != null){
                    V1ContainerPort port = new V1ContainerPort();
                    port.setContainerPort(deployConfigDTO.getContainerPort());
                    port.setProtocol("TCP");
                    List<V1ContainerPort> ports = new ArrayList<>();
                    ports.add(port);
                    container.setPorts(ports);
                }
                if(StringUtils.isNotEmpty(deployConfigDTO.getHealthCheckExec())){
                    container.setLivenessProbe(probe);
                }
                container.setVolumeMounts(containerVolumes);
                container.setResources(resource);

                containers.add(container);
            }

            V1PodSpec podSpec = new V1PodSpec();
            podSpec.setNodeSelector(serverLabels);
            podSpec.setVolumes(serverVolumes);
            podSpec.setContainers(containers);
            if(Boolean.TRUE.equals(deployConfigDTO.getUniqueDeploy())){
                //pod反亲和性实现唯一性部署

                V1LabelSelectorRequirement labelSelectRequirement = new V1LabelSelectorRequirement();
                labelSelectRequirement.setKey(deployConfigDTO.getDeployLabel().getKey());
                labelSelectRequirement.setOperator("In");
                labelSelectRequirement.setValues(Arrays.asList(deployConfigDTO.getDeployLabel().getValue()));
                V1LabelSelector labelSelector = new V1LabelSelector();
                labelSelector.setMatchExpressions(Arrays.asList(labelSelectRequirement));
                V1PodAffinityTerm podAffinityTerm = new V1PodAffinityTerm();
                podAffinityTerm.setLabelSelector(labelSelector);
                podAffinityTerm.setTopologyKey("kubernetes.io/hostname");
                V1PodAntiAffinity podAntiAffinity = new V1PodAntiAffinity();
                podAntiAffinity.setRequiredDuringSchedulingIgnoredDuringExecution(Arrays.asList(podAffinityTerm));

                V1Affinity affinity = new V1Affinity();
                affinity.setPodAntiAffinity(podAntiAffinity);
                podSpec.setAffinity(affinity);
            }

            V1PodTemplateSpec  podTemplateSpec = new V1PodTemplateSpec();
            podTemplateSpec.setMetadata(podMeta);
            podTemplateSpec.setSpec(podSpec);

            ExtensionsV1beta1DeploymentSpec deploymentSpec = new ExtensionsV1beta1DeploymentSpec();
            deploymentSpec.setProgressDeadlineSeconds(deployConfigDTO.getTimeOut());
            deploymentSpec.setReplicas(deployConfigDTO.getPods());
            deploymentSpec.setTemplate(podTemplateSpec);

            ExtensionsV1beta1Deployment appDeployment = new ExtensionsV1beta1Deployment();
            appDeployment.setMetadata(meta);
            appDeployment.setSpec(deploymentSpec);
            appDeployment.setKind("Deployment");
            ExtensionsV1beta1Deployment  deploymentInfo = apiInstance.createNamespacedDeployment(deployConfigDTO.getNamespace(),appDeployment,"");
            return true;
        }catch(Exception e){
            logger.error(e.getMessage());
        }
        return false;
    }

    @Override
    public ImageDeployInfoDTO getImageDeploymentInfo(String namespace,String name)throws IOException, ApiException{
        ImageDeployInfoDTO deployInfoDTO = new ImageDeployInfoDTO();
        ApiClient client = Config.defaultClient();
        Configuration.setDefaultApiClient(client);
        AppsV1beta1Api apiInstance = new AppsV1beta1Api();
        try{
            AppsV1beta1Deployment deployment =apiInstance.readNamespacedDeployment(name,namespace,null,null,null);
            AppsV1beta1DeploymentStatus status = deployment.getStatus();
            deployInfoDTO.setAvailable(Boolean.FALSE);
            for(AppsV1beta1DeploymentCondition condition: status.getConditions()){
                if(condition.getType().equalsIgnoreCase("Available") &&
                        condition.getStatus().equalsIgnoreCase("True")){
                    deployInfoDTO.setAvailable(Boolean.TRUE);
                }
            }
            deployInfoDTO.setPods(status.getReplicas()==null?0:status.getReplicas());
            deployInfoDTO.setAvailablePods(status.getReadyReplicas()==null?0:status.getReadyReplicas());
            deployInfoDTO.setUnavailablePods(status.getUnavailableReplicas()==null?0:status.getUnavailableReplicas());
        }catch (Exception e){
            logger.error(e.getMessage());
            deployInfoDTO=null;
        }
        return deployInfoDTO;
    }

    @Override
    public Boolean deleteImageDeployment(String namespace,String name)throws IOException, ApiException{
        ApiClient client = Config.defaultClient();
        Configuration.setDefaultApiClient(client);
        ExtensionsV1beta1Api  apiInstance = new ExtensionsV1beta1Api ();
        try{
            V1DeleteOptions options = new V1DeleteOptions();
            options.setGracePeriodSeconds((long)0);
            options.setPropagationPolicy("Background");
            V1Status status = apiInstance.deleteNamespacedDeployment(name,namespace,options,null,null,null,null);
            return true;
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return false;
    }

    @Override
    public ServiceDeployInfoDTO createServiceDeployment(ServiceConfigDTO serviceConfigDTO)throws IOException, ApiException{
        ServiceDeployInfoDTO serviceInfoDTO = new ServiceDeployInfoDTO();
        ApiClient client = Config.defaultClient();
        Configuration.setDefaultApiClient(client);
        CoreV1Api coreV1Api = new CoreV1Api();

        try{
            //服务标签
            Map<String,String> serviceLabel = new HashMap<>();
            serviceLabel.put(serviceConfigDTO.getNamespace(),serviceConfigDTO.getServiceName());

            V1ObjectMeta meta = new V1ObjectMeta();
            meta.setNamespace(serviceConfigDTO.getNamespace());
            meta.setName(serviceConfigDTO.getServiceName());
            meta.setLabels(serviceLabel);

            //pod标签
            Map<String,String> podLabel = new HashMap<>();
            for(LabelDTO label: serviceConfigDTO.getDeployLabels()){
                podLabel.put(label.getKey(),label.getValue());
            }

            V1ServiceSpec serviceSpec = new V1ServiceSpec();
            serviceSpec.setSelector(podLabel);
            V1ServicePort port = new V1ServicePort();
            port.setPort(serviceConfigDTO.getPodPort());
            IntOrString val = new IntOrString(serviceConfigDTO.getPodPort());
            port.setTargetPort(val);
            if(serviceConfigDTO.getType().equals(K8sAPPType.EXTERNAL_SERVICE)){
                port.setNodePort(serviceConfigDTO.getNodePort());
            }
            List<V1ServicePort> ports = new ArrayList<>();
            ports.add(port);
            serviceSpec.setPorts(ports);
            if(serviceConfigDTO.getType().equals(K8sAPPType.EXTERNAL_SERVICE)){
                serviceSpec.setType("NodePort");
            }else if(serviceConfigDTO.getType().equals(K8sAPPType.EXTERNAL_SERVICE)){
                serviceSpec.setType("ClusterIP");
            }

            V1Service service = new V1Service();
            service.setMetadata(meta);
            service.setSpec(serviceSpec);

            V1Service serviceInfo = coreV1Api.createNamespacedService(serviceConfigDTO.getNamespace(),service,null);
            String type = serviceInfo.getSpec().getType();
            switch (type){
                case "NodePort":
                    serviceInfoDTO.setType(K8sAPPType.EXTERNAL_SERVICE);
                    break;
                case "ClusterIP":
                    serviceInfoDTO.setType(K8sAPPType.INTERNAL_SERVICE);
                    break;
            }
            V1ServicePort servicePort = serviceInfo.getSpec().getPorts().get(0);
            serviceInfoDTO.setPodPort(servicePort.getPort());
            serviceInfoDTO.setPodPort(servicePort.getNodePort());
            serviceInfoDTO.setIp(serviceInfo.getSpec().getClusterIP());
        }catch (Exception e){
            logger.error(e.getMessage());
            serviceInfoDTO = null;
        }
        return serviceInfoDTO;
    }

    @Override
    public Boolean deleteServiceDeployment(String namespace,String name)throws IOException, ApiException{
        ApiClient client = Config.defaultClient();
        Configuration.setDefaultApiClient(client);
        CoreV1Api coreV1Api = new CoreV1Api();

        try{
            V1Status status = coreV1Api.deleteNamespacedService(name,namespace,null);
            return true;
        }catch(Exception e){
            logger.error(e.getMessage());
        }
        return false;
    }
    @Override
    public ServiceDeployInfoDTO getServiceDeploymentInfo(String namespace,String name)throws IOException, ApiException{
        ServiceDeployInfoDTO serviceInfoDTO = new ServiceDeployInfoDTO();
        ApiClient client = Config.defaultClient();
        Configuration.setDefaultApiClient(client);
        CoreV1Api coreV1Api = new CoreV1Api();
        try{
            V1Service result = coreV1Api.readNamespacedService(name,namespace,null,null,null);
            String type = result.getSpec().getType();
            switch (type){
                case "NodePort":
                    serviceInfoDTO.setType(K8sAPPType.EXTERNAL_SERVICE);
                    break;
                case "ClusterIP":
                    serviceInfoDTO.setType(K8sAPPType.INTERNAL_SERVICE);
                    break;
            }
            V1ServicePort servicePort = result.getSpec().getPorts().get(0);
            serviceInfoDTO.setPodPort(servicePort.getPort());
            serviceInfoDTO.setPodPort(servicePort.getNodePort());
            serviceInfoDTO.setIp(result.getSpec().getClusterIP());
        }catch(Exception e){
            logger.error(e.getMessage());
            serviceInfoDTO=null;
        }
        return serviceInfoDTO;
    }

    @Override
    public Boolean updateDeployPodsByName(String namespace,String deployName, int pods)throws IOException, ApiException {
        ApiClient client = Config.defaultClient();
        Configuration.setDefaultApiClient(client);
        ExtensionsV1beta1Api apiInstance = new ExtensionsV1beta1Api();
        pods = pods==0?1:pods;

        try{
            String patchStr = "{\"op\":\"replace\",\"path\":\"/spec/replicas\",\"value\":"+ pods +"}";
            Object patchObj = deserialize(patchStr);
            ArrayList<JsonObject> patchList = new ArrayList<>();
            patchList.add(((JsonElement) patchObj).getAsJsonObject());
            ExtensionsV1beta1Scale deploymentInfo = apiInstance.patchNamespacedDeploymentScale(deployName,namespace,patchList,null);
            return true;
        }catch(Exception e){
            logger.error(e.getMessage());
        }
        return false;
    }

    @Override
    public List<ServerInfoDTO> getAllServerNodes() throws IOException, ApiException{
        ApiClient client = Config.defaultClient();
        Configuration.setDefaultApiClient(client);
        CoreV1Api coreV1Api = new CoreV1Api();
        V1NodeList result = coreV1Api.listNode(null,null,null,null,null,0,null,0,null);
        List<ServerInfoDTO> serversList = new ArrayList<>();
        for(V1Node node : result.getItems()){
            ServerInfoDTO serverInfoDTO = new ServerInfoDTO();
            List<V1NodeAddress> addressList = node.getStatus().getAddresses();
            for(V1NodeAddress address : addressList){
                if(address.getType().equalsIgnoreCase("InternalIP")){
                    serverInfoDTO.setIpv4(address.getAddress());
                }
                if(address.getType().equalsIgnoreCase("HostName")){
                    serverInfoDTO.setHostname(address.getAddress());
                }
            }
            List<V1NodeCondition> conditions = node.getStatus().getConditions();
            serverInfoDTO.setStatus(Boolean.FALSE);
            for(V1NodeCondition condition: conditions){
                if(condition.getType().equalsIgnoreCase("Ready") &&
                        condition.getStatus().equalsIgnoreCase("True")){
                    serverInfoDTO.setStatus(Boolean.TRUE);
                }
            }
            Map<String,Quantity> capacitys = node.getStatus().getCapacity();
            serverInfoDTO.setCpu(capacitys.get("cpu").getNumber().toString());
            serverInfoDTO.setMemory(capacitys.get("memory").getNumber().toString());
            serverInfoDTO.setUid(node.getMetadata().getUid());
            serverInfoDTO.setLabels(node.getMetadata().getLabels());
            V1NodeSystemInfo nodeInfo = node.getStatus().getNodeInfo();
            serverInfoDTO.setDockerVersion(nodeInfo.getContainerRuntimeVersion());
            serverInfoDTO.setKernel(nodeInfo.getKernelVersion());
            serverInfoDTO.setOs(nodeInfo.getOsImage());
            serversList.add(serverInfoDTO);
        }
        return serversList;
    }
    @Override
    public Boolean createServerLabel(String hostName, List<LabelDTO> labels)throws IOException, ApiException{
        ApiClient client = Config.defaultClient();
        Configuration.setDefaultApiClient(client);
        CoreV1Api coreV1Api = new CoreV1Api();
        try{
            V1Node node = coreV1Api.readNode(hostName, null, null, null);
            Map<String,String> labelList = node.getMetadata().getLabels();
            for(LabelDTO labelDTO:labels){
                labelList.put(labelDTO.getKey(),labelDTO.getValue());
            }
            String patchStr = "{\"op\":\"replace\",\"path\":\"/metadata/labels\",\"value\":"+ JSON.toJSONString(labelList) +"}";
            Object patchObj = deserialize(patchStr);
            ArrayList<JsonObject> patchList = new ArrayList<>();
            patchList.add(((JsonElement) patchObj).getAsJsonObject());
            coreV1Api.patchNode(hostName,patchList,"");
            return true;
        }catch(Exception e){
            logger.error(e.getMessage());
        }
        return false;
    }

    @Override
    public Boolean deleteServerLabel(String hostName,List<LabelDTO> labels) throws IOException, ApiException{
        ApiClient client = Config.defaultClient();
        Configuration.setDefaultApiClient(client);
        CoreV1Api coreV1Api = new CoreV1Api();
        try{
            V1Node node = coreV1Api.readNode(hostName, null, null, null);
            Map<String,String> labelList = node.getMetadata().getLabels();
            for(LabelDTO labelDTO:labels){
                if(labelList.containsKey(labelDTO.getKey())){
                    labelList.remove(labelDTO.getKey());
                }
            }
            String patchStr = "{\"op\":\"replace\",\"path\":\"/metadata/labels\",\"value\":"+ JSON.toJSONString(labelList) +"}";
            Object patchObj = deserialize(patchStr);
            ArrayList<JsonObject> patchList = new ArrayList<>();
            patchList.add(((JsonElement) patchObj).getAsJsonObject());
            coreV1Api.patchNode(hostName,patchList,"");
            return true;
        }catch(Exception e){
            logger.error(e.getMessage());
        }
        return false;
    }

    @Override
    public ServerInfoDTO getServerInfoByHostname(String hostname) throws IOException, ApiException{
        ApiClient client = Config.defaultClient();
        Configuration.setDefaultApiClient(client);
        CoreV1Api coreV1Api = new CoreV1Api();

        try {
            V1Node node = coreV1Api.readNode(hostname, null, null, null);
            ServerInfoDTO serverInfoDTO = new ServerInfoDTO();
            List<V1NodeAddress> addressList = node.getStatus().getAddresses();
            for (V1NodeAddress address : addressList) {
                if (address.getType().equalsIgnoreCase("InternalIP")) {
                    serverInfoDTO.setIpv4(address.getAddress());
                }
                if (address.getType().equalsIgnoreCase("HostName")) {
                    serverInfoDTO.setHostname(address.getAddress());
                }
            }
            Map<String,Quantity> capacitys = node.getStatus().getCapacity();
            serverInfoDTO.setCpu(capacitys.get("cpu").getNumber().toString());
            serverInfoDTO.setMemory(capacitys.get("memory").getNumber().toString());
            List<V1NodeCondition> conditions = node.getStatus().getConditions();
            for (V1NodeCondition condition : conditions) {
                if (condition.getType().equalsIgnoreCase("Ready") &&
                        condition.getStatus().equalsIgnoreCase("True")) {
                    serverInfoDTO.setStatus(true);
                }
            }
            serverInfoDTO.setUid(node.getMetadata().getUid());
            serverInfoDTO.setLabels(node.getMetadata().getLabels());
            V1NodeSystemInfo nodeInfo = node.getStatus().getNodeInfo();
            serverInfoDTO.setDockerVersion(nodeInfo.getContainerRuntimeVersion());
            serverInfoDTO.setKernel(nodeInfo.getKernelVersion());
            serverInfoDTO.setOs(nodeInfo.getOsImage());
            return serverInfoDTO;
        }catch (Exception e){
            logger.error(e.getMessage());
            return null;
        }
    }

    @Override
    public List<ServerInfoDTO> getServerNodesByServerLabel(LabelDTO serverLabel)throws IOException, ApiException{
        List<ServerInfoDTO> serverList = new ArrayList<>();
        ApiClient client = Config.defaultClient();
        Configuration.setDefaultApiClient(client);
        CoreV1Api apiInstance = new CoreV1Api();
        try{
            String labelSelector=serverLabel.getKey()+"="+serverLabel.getValue();
            V1NodeList result = apiInstance.listNode(null,null, null, null, labelSelector, null, null, null, null);
            for(V1Node node:result.getItems()){
                ServerInfoDTO serverInfoDTO = new ServerInfoDTO();
                List<V1NodeAddress> addressList = node.getStatus().getAddresses();
                for (V1NodeAddress address : addressList) {
                    if (address.getType().equalsIgnoreCase("InternalIP")) {
                        serverInfoDTO.setIpv4(address.getAddress());
                    }
                    if (address.getType().equalsIgnoreCase("HostName")) {
                        serverInfoDTO.setHostname(address.getAddress());
                    }
                }
                Map<String,Quantity> capacitys = node.getStatus().getCapacity();
                serverInfoDTO.setCpu(capacitys.get("cpu").getNumber().toString());
                serverInfoDTO.setMemory(capacitys.get("memory").getNumber().toString());
                List<V1NodeCondition> conditions = node.getStatus().getConditions();
                serverInfoDTO.setStatus(Boolean.FALSE);
                for (V1NodeCondition condition : conditions) {
                    if (condition.getType().equalsIgnoreCase("Ready") &&
                            condition.getStatus().equalsIgnoreCase("True")) {
                        serverInfoDTO.setStatus(Boolean.TRUE);
                    }
                }
                serverInfoDTO.setUid(node.getMetadata().getUid());
                serverInfoDTO.setLabels(node.getMetadata().getLabels());
                V1NodeSystemInfo nodeInfo = node.getStatus().getNodeInfo();
                serverInfoDTO.setDockerVersion(nodeInfo.getContainerRuntimeVersion());
                serverInfoDTO.setKernel(nodeInfo.getKernelVersion());
                serverInfoDTO.setOs(nodeInfo.getOsImage());
                serverList.add(serverInfoDTO);
            }
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return serverList;
    }

    @Override
    public List<ServerInfoDTO> getServerNodesByDeployLabel(String namespace,LabelDTO deployLabel)throws IOException, ApiException{
        List<ServerInfoDTO> serverList = new ArrayList<>();
        ApiClient client = Config.defaultClient();
        Configuration.setDefaultApiClient(client);
        CoreV1Api apiInstance = new CoreV1Api();

        try{

            String labelSelector=deployLabel.getKey()+"="+deployLabel.getValue();
            V1PodList result = apiInstance.listNamespacedPod(namespace, null, null, null, null, labelSelector, null, null, 0, null);
            for(V1Pod pod: result.getItems()){
                ServerInfoDTO serverInfoDTO = new ServerInfoDTO();
                serverInfoDTO.setIpv4(pod.getStatus().getHostIP());
                serverInfoDTO.setHostname(pod.getSpec().getNodeName());

                serverList.add(serverInfoDTO);
            }
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return serverList;
    }

    @Override
    public JSONArray getServerCPUUsageRateByHostname(List<String> hostNames,long start,long end,int step){
        String hostName="";
        for(int i=0; i<hostNames.size(); i++){
            if(i > 0){
                hostName += "|"+hostNames.get(i);
            }else{
                hostName = hostNames.get(i);
            }
        }
        String query = "sum (rate (container_cpu_usage_seconds_total{id=\"/\",kubernetes_io_hostname=~\""+ hostName +"\"}[5m])) / sum (machine_cpu_cores{kubernetes_io_hostname=~\""+ hostName +"\"})";

        return getServerResourceInfo(query,start,end,step);
    }

    @Override
    public JSONArray getServerMemoryUsageRateByHostname(List<String> hostNames,long start,long end,int step){
        String hostName="";
        for(int i=0; i<hostNames.size(); i++){
            if(i > 0){
                hostName += "|"+hostNames.get(i);
            }else{
                hostName = hostNames.get(i);
            }
        }
        String query = "sum (rate (container_memory_working_set_bytes{id=\"/\",kubernetes_io_hostname=~\""+ hostName +"\"}[5m])) / sum (machine_memory_bytes{kubernetes_io_hostname=~\""+ hostName +"\"})";

        return getServerResourceInfo(query,start,end,step);
    }

    @Override
    public NetworkFlowDTO getServerNetworkUsageRateByHostname(List<String> hostNames,long start,long end,int step){
        NetworkFlowDTO networkFlowDTO = new NetworkFlowDTO();
        String hostName="";
        for(int i=0; i<hostNames.size(); i++){
            if(i > 0){
                hostName += "|"+hostNames.get(i);
            }else{
                hostName = hostNames.get(i);
            }
        }
        String receiveQuery = "sum (rate (container_network_receive_bytes_total{kubernetes_io_hostname=~\""+ hostName +"\"}[5m]))";
        String tramsmitQuery = "sum (rate (container_network_transmit_bytes_total{kubernetes_io_hostname=~\""+ hostName +"\"}[5m]))";
        String totalQuery = "sum (rate (container_network_receive_bytes_total{kubernetes_io_hostname=~\""+ hostName +"\"}[5m])+rate (container_network_transmit_bytes_total{kubernetes_io_hostname=~\""+ hostName +"\"}[5m]))";

        networkFlowDTO.setReceiveResult(getServerResourceInfo(receiveQuery,start,end,step));
        networkFlowDTO.setTransmitResult(getServerResourceInfo(tramsmitQuery,start,end,step));
        networkFlowDTO.setTotalResult(getServerResourceInfo(totalQuery,start,end,step));

        return  networkFlowDTO;
    }

    @Override
    public String getServerDiskByHostname(String hostName){
        String diskQuery = "sum(container_fs_limit_bytes{device=~\"^/.*$\",id=\"/\",kubernetes_io_hostname=~\""+ hostName +"\"})";
        return getHardRes(diskQuery);
    }

    @Override
    public List<PodDTO>  getPodsByCluster(LabelDTO label,List<String> hostnames) throws IOException, ApiException{
        List<PodDTO> podList = new ArrayList<>();
        ApiClient client = Config.defaultClient();
        Configuration.setDefaultApiClient(client);
        CoreV1Api apiInstance = new CoreV1Api();
        try{
            for(String hostname: hostnames){
                String fieldSelector= "spec.nodeName="+hostname;
                V1PodList result = apiInstance.listPodForAllNamespaces(null, fieldSelector, null, null, null, null, null, null, null);
                for(V1Pod pod: result.getItems()){
                    PodDTO podDTO = new PodDTO();
                    V1PodSpec podSpec = pod.getSpec();
                    if(podSpec.getNodeSelector() != null &&
                            podSpec.getNodeSelector().get(label.getKey()).equalsIgnoreCase(label.getValue())){
                        podDTO.setName(pod.getMetadata().getName());
                        podDTO.setPodIp(pod.getStatus().getPodIP());
                        podDTO.setStauts(pod.getStatus().getPhase());
                        podDTO.setPodContainers(podSpec.getContainers().size());

                        podList.add(podDTO);
                    }
                }
            }
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return podList;
    }

    /**
     * 根据查询参数获取服务器资源信息
     * @param query 查询字符串
     * @param start 开始时间，linux时间戳
     * @param end 结束时间，linux时间戳
     * @param step 步长，单位是s，按多少秒取数据点，默认是60s
     * @return 返回json字符串
     */
    private JSONArray getServerResourceInfo(String query,long start,long end,int step){
        Map<String,String> params = new HashMap<>();
        params.put("query",query);
        params.put("start",Long.toString(start/1000));
        params.put("end",Long.toString(end/1000));
        params.put("step",Integer.toString(step==0?60:step));

        try{
            String detail = HttpClientUtil.doGet(prometheusUrl+"query_range",params);
            JSONObject obj = JSON.parseObject(detail);
            if(obj.get("status").equals("success")){
                JSONObject dataObj = (JSONObject) obj.get("data");
                JSONArray reslutObj = (JSONArray) dataObj.get("result");
                return reslutObj;
            }else{
                return new JSONArray();
            }
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return new JSONArray();
    }

    /**
     * 获取硬件资源
     * @param query 查询参数
     * @return
     */
    private String getHardRes(String query){
        Map<String,String> params = new HashMap<>();
        params.put("query",query);
        try{
            String detail = HttpClientUtil.doGet(prometheusUrl+"query",params);
            JSONObject obj = JSON.parseObject(detail);
            if(obj.get("status").equals("success")){
                JSONObject dataObj = (JSONObject) obj.get("data");
                JSONArray reslutObj = (JSONArray) dataObj.get("result");
                JSONObject item = (JSONObject)reslutObj.get(0);
                JSONArray values = (JSONArray)item.get("value");
                return values.get(1).toString();
            }else{
                return "";
            }
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return "";
    }

    /**
     * 构造patch对象
     * @param jsonStr json字符串
     * @return
     */
    static public Object deserialize(String jsonStr) {
        Object obj = (new Gson()).fromJson(jsonStr, JsonElement.class);
        return obj;
    }

}

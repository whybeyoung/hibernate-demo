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
import com.iflytek.iaas.dto.k8s.*;
import com.iflytek.iaas.service.K8SService;
import com.iflytek.iaas.utils.HttpClientUtil;
import io.kubernetes.client.ApiClient;
import io.kubernetes.client.ApiException;
import io.kubernetes.client.Configuration;
import io.kubernetes.client.apis.AppsV1beta1Api;
import io.kubernetes.client.apis.CoreV1Api;
import io.kubernetes.client.custom.Quantity;
import io.kubernetes.client.models.*;
import io.kubernetes.client.util.Config;
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
public class k8sServiceImpl  implements K8SService {
    private Logger logger = LoggerFactory.getLogger(K8SService.class);

    @Value("${prometheus.url}")
    private String prometheusUrl;

    @Override
    public boolean createDeployNamespace(String namespace)throws IOException, ApiException{
        ApiClient client = Config.defaultClient();
        Configuration.setDefaultApiClient(client);
        CoreV1Api coreV1Api = new CoreV1Api();
        V1NamespaceList nsObj = coreV1Api.listNamespace("","","",false,"",0,"",5000,false);
        for(V1Namespace nsItem: nsObj.getItems()){
            if(nsItem.getMetadata().getName().equals(namespace)){
                return true;
            }
        }
        V1Namespace body = new V1Namespace();
        V1ObjectMeta meta = new V1ObjectMeta();
        meta.setName(namespace);
        body.setKind("Namespace");
        body.setMetadata(meta);
        try{
            V1Namespace ns = coreV1Api.createNamespace(body,"");
            return true;
        }catch (Exception e){
            logger.error(e.getMessage());
        }

        return false;
    }

    @Override
    public List<NamespaceDTO> getNamespaces() throws IOException, ApiException{
        ApiClient client = Config.defaultClient();
        Configuration.setDefaultApiClient(client);
        CoreV1Api coreV1Api = new CoreV1Api();
        V1NamespaceList nsObj = coreV1Api.listNamespace("","","",false,"",0,"",5000,false);
        List<NamespaceDTO> nsList = new ArrayList<>();
        for(V1Namespace nsItem: nsObj.getItems()){
            NamespaceDTO namespaceDTO = new NamespaceDTO();
            namespaceDTO.setNameSpace(nsItem.getMetadata().getName());
            namespaceDTO.setUid(nsItem.getMetadata().getUid());
            nsList.add(namespaceDTO);
        }
        return nsList;
    }

    @Override
    public boolean deleteNamespace(NamespaceDTO ns)throws IOException, ApiException {
        ApiClient client = Config.defaultClient();
        Configuration.setDefaultApiClient(client);
        CoreV1Api coreV1Api = new CoreV1Api();

        V1Preconditions preconditions = new V1Preconditions();
        preconditions.setUid(ns.getUid());
        V1DeleteOptions options = new V1DeleteOptions();
//        options.setKind("Namespace");
        options.setPreconditions(preconditions);

        try {
            coreV1Api.deleteNamespace(ns.getNameSpace(), options, "", 0, false, "");
            return true;
        }catch(Exception e){
            logger.error(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean createImageDeployment(DeployConfigDTO deployConfigDTO)throws IOException, ApiException {
        ApiClient client = Config.defaultClient();
        Configuration.setDefaultApiClient(client);
        AppsV1beta1Api apiInstance = new AppsV1beta1Api();

        try{
            //部署标签
            Map<String,String> deployLabel = new HashMap<>();
            deployLabel.put("image_deploy",deployConfigDTO.getImgName());

            V1ObjectMeta meta = new V1ObjectMeta();
            meta.setName(deployConfigDTO.getImgName());
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
            for(MountVolumeDTO mountVolumeDTO:deployConfigDTO.getMountDirs()){
                V1Volume volume = new V1Volume();
                volume.setName(mountVolumeDTO.getName());
                V1HostPathVolumeSource volumeSource = new V1HostPathVolumeSource();
                volumeSource.setPath(mountVolumeDTO.getServerDir());
                volumeSource.setType("DirectoryOrCreate");
                volume.setHostPath(volumeSource);
                serverVolumes.add(volume);
            }
            //启动命令
            List<String> cmds = new ArrayList<>();
            cmds.add(deployConfigDTO.getInitCmd());
            //环境变量
            List<V1EnvVar> envs = new ArrayList<>();
            for(EnvDTO env: deployConfigDTO.getEnvs()){
                V1EnvVar item = new V1EnvVar();
                item.setName(env.getKey());
                item.setValue(env.getValue());
                envs.add(item);
            }
            //健康检查
            V1Probe probe = new V1Probe();
            //容器中需要挂载的目录
            List<V1VolumeMount> containerVolumes = new ArrayList<>();
            for(MountVolumeDTO mountVolumeDTO:deployConfigDTO.getMountDirs()){
                V1VolumeMount volumeMount = new V1VolumeMount();
                volumeMount.setName(mountVolumeDTO.getName());
                volumeMount.setMountPath(mountVolumeDTO.getContainerDir());
                containerVolumes.add(volumeMount);
            }
            //资源限制
            V1ResourceRequirements resource = new V1ResourceRequirements();
            Quantity cpuQuantity = new Quantity(new BigDecimal(deployConfigDTO.getMemoryLimits()),Quantity.Format.BINARY_SI);
            Map<String,Quantity> limit = new HashMap<>();
            limit.put("memory",cpuQuantity);

            List<V1Container> containers = new ArrayList<>();
            for(int i=0;i <deployConfigDTO.getPodContainers();i++){
                V1Container container = new V1Container();
                container.setCommand(cmds);
                container.setImage(deployConfigDTO.getImgPath());
                container.setName(deployConfigDTO.getImgName()+"-"+Integer.toString(i));
                container.setEnv(envs);
//                container.setLivenessProbe(probe);
                container.setVolumeMounts(containerVolumes);
                container.setResources(resource);

                containers.add(container);
            }


            V1PodSpec podSpec = new V1PodSpec();
            podSpec.setNodeSelector(serverLabels);
            podSpec.setVolumes(serverVolumes);
            podSpec.setContainers(containers);

            V1PodTemplateSpec  podTemplateSpec = new V1PodTemplateSpec();
            podTemplateSpec.setMetadata(podMeta);
            podTemplateSpec.setSpec(podSpec);


            AppsV1beta1DeploymentSpec deploymentSpec = new AppsV1beta1DeploymentSpec();
            deploymentSpec.setProgressDeadlineSeconds(deployConfigDTO.getTimeOut());
            deploymentSpec.setReplicas(deployConfigDTO.getPods());
            deploymentSpec.setTemplate(podTemplateSpec);

            AppsV1beta1Deployment appDeployment = new AppsV1beta1Deployment();
            appDeployment.setMetadata(meta);
            appDeployment.setSpec(deploymentSpec);
            appDeployment.setKind("Deployment");
            AppsV1beta1Deployment deploymentInfo = apiInstance.createNamespacedDeployment(deployConfigDTO.getNamespace(),appDeployment,"");
            return true;
        }catch(Exception e){
            logger.error(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean createServiceDeployment(ServiceConfigDTO serviceConfigDTO)throws IOException, ApiException{
        ApiClient client = Config.defaultClient();
        Configuration.setDefaultApiClient(client);
        CoreV1Api coreV1Api = new CoreV1Api();

        try{
            //服务标签
            Map<String,String> serviceLabel = new HashMap<>();
            serviceLabel.put(serviceConfigDTO.getNamespace(),serviceConfigDTO.getServerName());

            V1ObjectMeta meta = new V1ObjectMeta();
            meta.setNamespace(serviceConfigDTO.getServerName());
            meta.setName(serviceConfigDTO.getServerName());
            meta.setLabels(serviceLabel);

            //pod标签
            Map<String,String> podLabel = new HashMap<>();
            for(LabelDTO label: serviceConfigDTO.getDeployLabels()){
                podLabel.put(label.getKey(),label.getValue());
            }

            V1ServiceSpec serviceSpec = new V1ServiceSpec();
            serviceSpec.setSelector(podLabel);

            V1Service service = new V1Service();
            service.setMetadata(meta);
            service.setSpec(serviceSpec);

            V1Service serviceInfo = coreV1Api.createNamespacedService(serviceConfigDTO.getNamespace(),service,"");
            return true;
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return false;
    }

    @Override
    public DeployInfoDTO getImageDeploymentInfo(String namespace,String name)throws IOException, ApiException{
        DeployInfoDTO deployInfoDTO = new DeployInfoDTO();
        ApiClient client = Config.defaultClient();
        Configuration.setDefaultApiClient(client);
        AppsV1beta1Api apiInstance = new AppsV1beta1Api();
        try{
            AppsV1beta1Deployment deployment =apiInstance.readNamespacedDeployment(name,namespace,"",false,false);
            AppsV1beta1DeploymentStatus status = deployment.getStatus();
            for(AppsV1beta1DeploymentCondition condition: status.getConditions()){
                if(condition.getType().equalsIgnoreCase("Available")){
                    if(condition.getStatus().equalsIgnoreCase("True")){
                        deployInfoDTO.setAvailable(true);
                    }else{
                        deployInfoDTO.setAvailable(false);
                    }
                }
            }
            deployInfoDTO.setPods(status.getReplicas()==null?0:status.getReplicas());
            deployInfoDTO.setAvailablePods(status.getReadyReplicas()==null?0:status.getReadyReplicas());
            deployInfoDTO.setUnavailablePods(status.getUnavailableReplicas()==null?0:status.getUnavailableReplicas());
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return deployInfoDTO;
    }
    @Override
    public boolean deleteServiceDeployment(String namespace,String name)throws IOException, ApiException{
        ApiClient client = Config.defaultClient();
        Configuration.setDefaultApiClient(client);
        CoreV1Api coreV1Api = new CoreV1Api();

        try{
            V1Status status = coreV1Api.deleteNamespacedService(name,namespace,"");
            return true;
        }catch(Exception e){
            logger.error(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean deleteImageDeployment(String namespace,String name)throws IOException, ApiException{
        ApiClient client = Config.defaultClient();
        Configuration.setDefaultApiClient(client);
        AppsV1beta1Api apiInstance = new AppsV1beta1Api();
        try{
            V1DeleteOptions options = new V1DeleteOptions();

            V1Status status = apiInstance.deleteNamespacedDeployment(name,namespace,options,"pretty",10,true,"policy");
            return true;
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean updateDeployPodsByName(String namespace,String deployName, int pods)throws IOException, ApiException {
        ApiClient client = Config.defaultClient();
        Configuration.setDefaultApiClient(client);
        AppsV1beta1Api apiInstance = new AppsV1beta1Api();

        try{
            String patchStr = "{\"op\":\"replace\",\"path\":\"/spec/replicas\",\"value\":"+ pods +"}";
            Object patchObj = deserialize(patchStr);
            ArrayList<JsonObject> patchList = new ArrayList<>();
            patchList.add(((JsonElement) patchObj).getAsJsonObject());
            AppsV1beta1Scale deploymentInfo = apiInstance.patchNamespacedDeploymentScale(deployName,namespace,patchList,"");
            return true;
        }catch(Exception e){
            logger.error(e.getMessage());
        }
        return false;
    }

    @Override
    public List<ServerInfoDTO> getOnlineServerNodes() throws IOException, ApiException{
        ApiClient client = Config.defaultClient();
        Configuration.setDefaultApiClient(client);
        CoreV1Api coreV1Api = new CoreV1Api();
        V1NodeList result = coreV1Api.listNode("","","",false,"",0,"",0,false);
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
            for(V1NodeCondition condition: conditions){
                if(condition.getType().equalsIgnoreCase("True")){
                    serverInfoDTO.setStatus(true);
                }else{
                    serverInfoDTO.setStatus(false);
                }
            }
            Map<String,Quantity> capacitys = node.getStatus().getCapacity();
            serverInfoDTO.setCpu(capacitys.get("cup").getNumber().toString());
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
    public boolean createServerLabel(String hostName, List<LabelDTO> labels)throws IOException, ApiException{
        ApiClient client = Config.defaultClient();
        Configuration.setDefaultApiClient(client);
        CoreV1Api coreV1Api = new CoreV1Api();
        try{
            V1Node node = coreV1Api.readNode(hostName, "", false, false);
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
    public boolean deleteServerLabel(String hostName,List<LabelDTO> labels) throws IOException, ApiException{
        ApiClient client = Config.defaultClient();
        Configuration.setDefaultApiClient(client);
        CoreV1Api coreV1Api = new CoreV1Api();
        try{
            V1Node node = coreV1Api.readNode(hostName, "", false, false);
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
    public ServerInfoDTO getServerInfoByName(String hostName) throws IOException, ApiException{
        ApiClient client = Config.defaultClient();
        Configuration.setDefaultApiClient(client);
        CoreV1Api coreV1Api = new CoreV1Api();
        ServerInfoDTO serverInfoDTO = new ServerInfoDTO();
        try {
            V1Node node = coreV1Api.readNode(hostName, "", false, false);

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
            serverInfoDTO.setCpu(capacitys.get("cup").getNumber().toString());
            serverInfoDTO.setMemory(capacitys.get("memory").getNumber().toString());
            List<V1NodeCondition> conditions = node.getStatus().getConditions();
            for (V1NodeCondition condition : conditions) {
                if (condition.getType().equalsIgnoreCase("True")) {
                    serverInfoDTO.setStatus(true);
                }else{
                    serverInfoDTO.setStatus(false);
                }
            }
            serverInfoDTO.setUid(node.getMetadata().getUid());
            serverInfoDTO.setLabels(node.getMetadata().getLabels());
            V1NodeSystemInfo nodeInfo = node.getStatus().getNodeInfo();
            serverInfoDTO.setDockerVersion(nodeInfo.getContainerRuntimeVersion());
            serverInfoDTO.setKernel(nodeInfo.getKernelVersion());
            serverInfoDTO.setOs(nodeInfo.getOsImage());
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return serverInfoDTO;
    }

    @Override
    public String getServerCPUUsageRateByHostname(List<String> hostNames,long start,long end,int step){
        String hostName="";
        for(int i=0; i<hostNames.size(); i++){
            if(i > 0){
                hostName += "|"+hostNames.get(i);
            }else{
                hostName = hostNames.get(i);
            }
        }
        String query = "sum (rate (container_cpu_usage_seconds_total{id=\"/\",kubernetes_io_hostname=~\"\"+ hostName +\"\\\"}[5m])) / sum (machine_cpu_cores{kubernetes_io_hostname=~\\\"\"+ hostName +\"\"})*10000";

        return getServerResourceInfo(query,start,end,step);
    }

    @Override
    public String getServerMemoryUsageRateByHostname(List<String> hostNames,long start,long end,int step){
        String hostName="";
        for(int i=0; i<hostNames.size(); i++){
            if(i > 0){
                hostName += "|"+hostNames.get(i);
            }else{
                hostName = hostNames.get(i);
            }
        }
        String query = "sum (rate (container_memory_working_set_bytes{id=\"/\",kubernetes_io_hostname=~\"\"+ hostName +\"\\\"}[5m])) / sum (machine_memory_bytes{kubernetes_io_hostname=~\\\"\"+ hostName +\"\"})*10000";

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
        String tramsmitQuery = "sum (rate (container_network_transmit_bytes_total{kubernetes_io_hostname=~\""+ hostName +"\"}[2m]))";
        String totalQuery = "sum (rate (container_network_receive_bytes_total{kubernetes_io_hostname=~\""+ hostName +"\"}[2m])+rate (container_network_transmit_bytes_total{kubernetes_io_hostname=~\""+ hostName +"\"}[2m]))";

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

    /**
     * 根据查询参数获取服务器资源信息
     * @param query 查询字符串
     * @param start 开始时间，linux时间戳
     * @param end 结束时间，linux时间戳
     * @param step 步长，单位是s，按多少秒取数据点，默认是60s
     * @return 返回json字符串
     */
    private String getServerResourceInfo(String query,long start,long end,int step){
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
                return reslutObj.toJSONString();
            }else{
                return "";
            }
        }catch (Exception e){
            logger.error(e.getMessage());
        }
        return "";
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

    public static void main(String[] args) throws IOException, ApiException {


        ApiClient client = Config.defaultClient();
        Configuration.setDefaultApiClient(client);

        CoreV1Api api = new CoreV1Api();

        V1Status stat = api.deleteNamespace("test",new V1DeleteOptions(),"",0,false,"");



//        V1NodeList result = api.listNode("","","",false,"",0,"",0,false);
//        V1Node result = api.readNodeStatus("itesttech-172-31-1-157","");

        V1NodeSpec spec1 = new V1NodeSpec();
        V1Taint taint = new V1Taint();
//        taint.setEffect("NoExecute");
//        taint.setKey("fdfsfd");
//        taint.setValue("hhhh");
        List<V1Taint> taints = new ArrayList<>();
        taints.add(taint);
        spec1.setTaints(taints);
        V1ObjectMeta meta2 = new V1ObjectMeta();
        meta2.setName("itesttech-172-31-1-157");
//        meta2.setUid("58e889c8-3d70-11e8-bae2-d00dc25c8537");
        Map<String,String> labels = new HashMap<>();
        labels.put("beta.kubernetes.io/arch","amd64");
        labels.put("beta.kubernetes.io/os","linux");
        labels.put("kubernetes.io/hostname","itesttech-172-31-1-157");
        labels.put("testlabel","testlabel");
        meta2.setLabels(labels);

        Object obj = deserialize(JSON.toJSONString(meta2));
        V1Node result = api.patchNode("itesttech-172-31-1-157",obj,"");

//        AppsV1beta1DeploymentList result = appApi.listNamespacedDeployment("database","","","",false,"",0,"",0,false);

        System.out.print(JSON.toJSONString(result));
    }
}

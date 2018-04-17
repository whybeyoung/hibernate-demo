/**
 * Copyright (C), 科大讯飞股份有限公司
 * FileName: k8sServiceTest
 */
package iaas;

import com.alibaba.fastjson.JSON;
import com.iflytek.iaas.Application;
import com.iflytek.iaas.consts.K8sAPPType;
import com.iflytek.iaas.dto.k8s.*;
import com.iflytek.iaas.service.K8SService;
import com.iflytek.iaas.utils.ToolUtils;
import io.kubernetes.client.ApiClient;
import io.kubernetes.client.ApiException;
import io.kubernetes.client.Configuration;
import io.kubernetes.client.apis.AppsV1beta1Api;
import io.kubernetes.client.apis.CoreV1Api;
import io.kubernetes.client.custom.Quantity;
import io.kubernetes.client.models.*;
import io.kubernetes.client.util.Config;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 〈k8s接口测试〉
 *
 * @author xwliu
 * @create 2018/4/11
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class k8sServiceTest {

    @Autowired
    private K8SService k8SService;

    @Test
    public void createDeployNamespaceTest()throws IOException, ApiException{

        String namespace = "test111";
        boolean flag = k8SService.createDeployNamespace(namespace);

        System.out.print(flag);

    }

    @Test
    public void getNamespacesTest() throws IOException, ApiException{
        List<NamespaceDTO> nsList = k8SService.getNamespaces();
        System.out.print(JSON.toJSONString(nsList));
    }

    @Test
    public void deleteNamespaceTest()throws IOException, ApiException{
        NamespaceDTO  nsDto = new NamespaceDTO();
        nsDto.setNameSpace("test111");
//        nsDto.setUid("cf6ccf08-3ebd-11e8-bae2-d00dc25c8537");
        boolean flag = k8SService.deleteNamespace(nsDto);
        System.out.print(flag);
    }

    @Test
    public void getOnlineServerNodesTest() throws IOException, ApiException{
        List<ServerInfoDTO> servers = k8SService.getAllServerNodes();
        System.out.print(JSON.toJSONString(servers));
    }

    @Test
    public void getServerInfoByNameTest() throws IOException, ApiException{
        String hostName= "itesttech-172-31-1-157";
        ServerInfoDTO server = k8SService.getServerInfoByHostname(hostName);
        System.out.print(JSON.toJSONString(server));
    }

    @Test
    public void createServerLabel()throws IOException, ApiException{
        String hostName="itesttech-172-31-1-157";
        List<LabelDTO> labels = new ArrayList<>();
        LabelDTO labelDTO = new LabelDTO();
        labelDTO.setKey("label/server");
        labelDTO.setValue("test");
        labels.add(labelDTO);
        boolean flag = k8SService.createServerLabel(hostName,labels);
        System.out.print(flag);
    }

    @Test
    public void deleteServerLabelTest() throws IOException, ApiException{
        String hostName="itesttech-172-31-1-157";
        List<LabelDTO> labels = new ArrayList<>();
        LabelDTO labelDTO = new LabelDTO();
        labelDTO.setKey("test");
        labelDTO.setValue("test");
        labels.add(labelDTO);
        boolean flag = k8SService.deleteServerLabel(hostName,labels);
        System.out.print(flag);
    }

    @Test
    public void createDeployTest()throws IOException, ApiException{
        List<EnvDTO> envs = new ArrayList<>();
        EnvDTO env = new EnvDTO();
        env.setKey("MYSQL_ROOT_PASSWORD");
        env.setValue("iflytek_migu");
        envs.add(env);
        List<MountVolumeDTO> mountVolumes = new ArrayList<>();
        MountVolumeDTO mountVolumeDTO = new MountVolumeDTO();
        mountVolumeDTO.setContainerDir("mysql-pv-claim");
        mountVolumeDTO.setServerDir("/var/lib/mysql");
        mountVolumeDTO.setName("volume-"+ ToolUtils.getUniqueId());
        mountVolumes.add(mountVolumeDTO);
        LabelDTO serverLabel = new LabelDTO();
        serverLabel.setKey("label/server");
        serverLabel.setValue("test");
        DeployConfigDTO deployConfigDTO = new DeployConfigDTO();
        deployConfigDTO.setImgName("mysql-5-7");
//        deployConfigDTO.setImgName("node-exporter-latest");
        deployConfigDTO.setNamespace("test");
        deployConfigDTO.setImgPath("harbour.iflytek.com/library/mysql:5.7");
//        deployConfigDTO.setImgPath("harbour.iflytek.com/migu/node-exporter:latest");
        deployConfigDTO.setEnvs(envs);
        deployConfigDTO.setMountDirs(mountVolumes);
        deployConfigDTO.setInitCmd("");
        deployConfigDTO.setPods(1);
        deployConfigDTO.setMinPods(0);
        deployConfigDTO.setMaxPods(0);
        deployConfigDTO.setMemoryLimits("200");
        deployConfigDTO.setServerLabel(serverLabel);
        deployConfigDTO.setTimeOut(2000);
        deployConfigDTO.setUniqueDeploy(false);
        deployConfigDTO.setHealthCheck(false);

        boolean flag = k8SService.createImageDeployment(deployConfigDTO);
        System.out.print(flag);
    }

    @Test
    public void deleteImageDeploymentTest()throws IOException, ApiException{
        String namespace="test";
        String name = "mysql-5-7";
        boolean flag = k8SService.deleteImageDeployment(namespace,name);
        System.out.print(flag);
    }

    @Test
    public void getImageDeploymentInfoTest()throws IOException, ApiException{
        String namespace="test";
        String name = "node-exporter-latest";
        k8SService.getImageDeploymentInfo(namespace,name);
    }

    @Test
    public void createServiceDeployment()throws IOException, ApiException{
        List<String> imgList = new ArrayList<>();
        imgList.add("mysql-5-7");
        imgList.add("node-exporter-latest");
        ServiceConfigDTO serviceConfigDTO = new ServiceConfigDTO();
        serviceConfigDTO.setImgNames(imgList);
        serviceConfigDTO.setNamespace("test");
        serviceConfigDTO.setServerName("test-service");
        serviceConfigDTO.setType(K8sAPPType.INTERNAL_SERVICE);
        serviceConfigDTO.setPodPort(36);
        boolean flag = k8SService.createServiceDeployment(serviceConfigDTO);
        System.out.print(flag);
    }

    @Test
    public void deleteServiceDeployment()throws IOException, ApiException{
        String namespace="test";
        String name="test-service";
        k8SService.deleteServiceDeployment(namespace,name);
    }

    @Test
    public void getServiceDeploymentInfo()throws IOException, ApiException{
        String namespace="test";
        String name="test-service";
        k8SService.getServiceDeploymentInfo(namespace,name);
    }
    @Test
    public  void updateDeployPodsByNameTest()throws IOException, ApiException{
        String namespace="test";
        String deployName="node-exporter-latest";
        int pods=2;
        boolean flag = k8SService.updateDeployPodsByName(namespace,deployName,pods);
        System.out.print(flag);
    }

    @Test
    public void getServerNodesByLabelTest()throws IOException, ApiException{
        LabelDTO label = new LabelDTO();
        label.setKey("beta.kubernetes.io/arch");
        label.setValue("amd64");
        List<ServerInfoDTO> servers = k8SService.getServerNodesByLabel(label);
        System.out.print(JSON.toJSONString(servers));
    }

    @Test
    public void getServerHardResourceTest(){
        String hostname ="itesttech-172-31-1-157";
        String disk= k8SService.getServerDiskByHostname(hostname);
        System.out.print(disk);
    }

}

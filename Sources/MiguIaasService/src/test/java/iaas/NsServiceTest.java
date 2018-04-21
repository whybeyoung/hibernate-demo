/**
 * Copyright (C), 科大讯飞股份有限公司
 * FileName: UserServiceTest
 */
package iaas;

import com.iflytek.iaas.Application;
import com.iflytek.iaas.service.K8SService;
import com.iflytek.iaas.service.NameSpaceService;
import com.iflytek.iaas.vo.NameSpaceVO;
import io.kubernetes.client.ApiException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 〈用户服务测试类〉
 *
 * @author xwliu
 * @create 2018/4/2
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class NsServiceTest {

    @Autowired
    private NameSpaceService nsService;

    @Autowired
    private K8SService k8SService;

    @Test
    public void saveNS() throws IOException, ApiException {

        List<String> nsList = k8SService.getNamespaces();
        List<String> existList = nsService.searchNsList(null);
        nsList.stream()
                .filter(p -> !existList.contains(p))
                .map(ns -> {
                    NameSpaceVO vo = new NameSpaceVO();
                    vo.setNs(ns);
                    vo.setAnnotation(ns + " Annotation");
                    vo.setCreator("test1");
                    return nsService.saveNs(vo);
                }).forEach(id -> System.out.println(id));

    }
}

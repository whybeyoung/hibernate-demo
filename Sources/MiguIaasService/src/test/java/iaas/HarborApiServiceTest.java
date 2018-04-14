/**
 * Copyright (C), 科大讯飞股份有限公司
 * FileName: HarborApiServiceTest
 */
package iaas;

import com.iflytek.iaas.Application;
import com.iflytek.iaas.config.ImageHubConfig;
import com.iflytek.iaas.http.HttpAPIService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 〈用户服务测试类〉
 *
 * @author xwliu
 * @create 2018/4/2
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HarborApiServiceTest {


    @Autowired
    private ImageHubConfig hubConfig;


    @Autowired
    private HttpAPIService httpAPIService;

    @Test
    public void test() throws Exception {
        String str = httpAPIService.doGet(hubConfig.getHub_api() + "/api/repositories/migu/tiller/tags");
//        String str = httpAPIService.doGet("https://harbour.iflytek.com");
        System.out.println(str);
    }
}

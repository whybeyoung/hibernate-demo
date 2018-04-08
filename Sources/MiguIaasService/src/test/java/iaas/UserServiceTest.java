/**
 * Copyright (C), 科大讯飞股份有限公司
 * FileName: UserServiceTest
 */
package iaas;

import com.alibaba.fastjson.JSON;
import com.iflytek.iaas.Application;
import com.iflytek.iaas.dto.UserDTO;
import com.iflytek.iaas.service.UserService;
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
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void getUserInfoByIdTest(){
        String id = "123";
        UserDTO userDTO = userService.getUserInfoById(id);
        System.out.print(JSON.toJSONString(userDTO));
    }
}

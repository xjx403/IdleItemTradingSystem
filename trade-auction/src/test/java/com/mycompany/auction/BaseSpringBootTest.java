package com.mycompany.auction;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @version 1.0
 * @Author xjx
 * @Date 2024/4/15 17:00
 * @注释
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc //不启动服务器,使用mockMvc进行测试http请求。启动了完整的Spring应用程序上下文，但没有启动服务器
public class BaseSpringBootTest {

    protected Logger log = LoggerFactory.getLogger(BaseSpringBootTest.class);
}

package com.trade.mbg;

import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 * @version 1.0
 * @Author xjx
 * @Date 2024/3/18 19:51
 * @注释
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc //不启动服务器,使用mockMvc进行测试http请求。启动了完整的Spring应用程序上下文，但没有启动服务器
public class BaseSpringBootTest {

    protected Logger log = LoggerFactory.getLogger(MemberControllerTest.class);
}

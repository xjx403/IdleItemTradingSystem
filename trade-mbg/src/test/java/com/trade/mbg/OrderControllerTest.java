package com.trade.mbg;

import com.trade.mbg.service.OrderService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * @version 1.0
 * @Author xjx
 * @Date 2024/3/25 16:33
 * @注释
 */
public class OrderControllerTest extends BaseSpringBootTest{
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private OrderService orderService;
    @Test
    public void updateTest() throws Exception{
        String url = "/order/insert";
        String order = "{\n" +
                "  \"id\": \"null\",\n" +
                "  \"userId\": 1,\n" +
                "  \"createTime\": \"2024-03-25T08:36:49.806Z\",\n" +
                "  \"totalAmount\": 1,\n" +
                "  \"payWay\": 1,\n" +
                "  \"status\": 0,\n" +
                "  \"remarks\": \"test\",\n" +
                "  \"isDeleted\": 0\n" +
                "}";
        String result = mockMvc.perform(MockMvcRequestBuilders.post(url)
                .content(order)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }

    @Test
    public void testDelOrder(){
        System.out.println(orderService.removeById(1l));
    }

}

package com.trade.mbg;

import com.trade.mbg.controller.MemberController;
import com.trade.mbg.service.MemberService;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @version 1.0
 * @Author xjx
 * @Date 2024/3/18 19:49
 * @注释
 */

public class MemberControllerTest extends BaseSpringBootTest{
    @Autowired
    private MemberController memberController;

    @Autowired
    private MemberService memberService;
    @Autowired
    private MockMvc mockMvc;

    /**
     *     @Test
     *     public void greetingShouldReturnMessageFromService() throws Exception {
     *         //模拟userService.findByUserId(1)的行为
     *         when(userService.findByUserId(1)).thenReturn(new User(1,"张三"));
     *
     *         String result = this.mockMvc.perform(get("/user/1"))
     *                 .andDo(print())
     *                 .andExpect(status().isOk())
     *                 .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
     *                 .andExpect(jsonPath("$.name").value("张三"))
     *                 .andReturn().getResponse().getContentAsString();
     * */
    @Test
    public void demo() throws Exception {


        String url = "/user/register";
        //构造Request,

        for (int i = 0; i < 50; i++) {
            int start = 123;
            String username = "test" + i;
            String email = (start + i) + "@qq.com";
            String result = mockMvc.perform(MockMvcRequestBuilders.post(url)
                    .param("username", username)
                    .param("password", "123")
                    .param("email", email)
                    .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                    .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                    //.andDo(MockMvcResultHandlers.print())
                    .andExpect(status().isOk())
                    .andReturn().getResponse().getContentAsString();
            System.out.println("result:" + result);
        }
    }


    @Test
    public void  testDelUser(){
        System.out.println(memberService.removeById(52l));
    }
}

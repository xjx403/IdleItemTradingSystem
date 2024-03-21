package com.trade.mbg;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.trade.mbg.controller.ProductController;
import com.trade.mbg.entity.Picture;
import com.trade.mbg.entity.Product;
import com.trade.mbg.service.PictureService;
import com.trade.mbg.service.ProductService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;


/**
 * @version 1.0
 * @Author xjx
 * @Date 2024/3/18 21:27
 * @注释
 */
public class ProductControllerTest extends BaseSpringBootTest{

    @Autowired
    private ProductController productController;
    @Autowired
    private ProductService productService;
    @Autowired
    private PictureService pictureService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void updateTest() throws Exception{
        String url = "/product/update";

        String result = mockMvc.perform(MockMvcRequestBuilders.post(url)
                .param("sellerId", "4")
                .param("productName", "测试商品01")
                .param("productPrice", "582.12")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        System.out.println(result);
    }


    @Test
    public void updateWrapperTest(){
        UpdateWrapper<Product> updateWrapper = new UpdateWrapper<>();
        updateWrapper.ge("id", 4);
        updateWrapper.set("picture", "http://localhost/backEnd/picture/get?type=2&ownerId=4");
        System.out.println(productService.update(updateWrapper));
    }

    @Test
    public void get(){
        List<Product> list = productService.list();
        for (Product product: list) {
            System.out.println(product.toString());
        }
    }


}

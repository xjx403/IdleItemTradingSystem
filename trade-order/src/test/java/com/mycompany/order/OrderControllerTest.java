package com.mycompany.order;

import com.trade.mbg.entity.OrderItem;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

/**
 * @version 1.0
 * @Author xjx
 * @Date 2024/4/6 21:53
 * @注释
 */
public class OrderControllerTest extends BaseSpringBootTest{

    @Autowired
    private RestTemplate restTemplate;
    @Value("${trade.host.url.mbg:#{null}}")
    private String mbgHostUrl;

//    @Test
//    public void  test(){
//        OrderItem orderItem = new OrderItem();
//        orderItem.setOrderId(1l);
//        orderItem.setProductId(2l);
//        orderItem.setEndPrice(new BigDecimal("123.12"));
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        HttpEntity<OrderItem> entity = new HttpEntity<>(orderItem, headers);
//        System.out.println(entity);
//        System.out.println(mbgHostUrl + "/orderItem/insert");
//        ResponseEntity<OrderItem> responseEntity = restTemplate.postForEntity(mbgHostUrl + "/orderItem/insert", entity, OrderItem.class);
//        System.out.println(responseEntity.getBody());
//    }
}

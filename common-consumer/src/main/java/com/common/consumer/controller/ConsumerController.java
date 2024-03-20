package com.common.consumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.websocket.server.PathParam;

/**
 * @version 1.0
 * @Author xjx
 * @Date 2024/1/3 17:49
 * @注释
 */
@RestController
public class ConsumerController {
    @Autowired
    private LoadBalancerClient loadBalancerClient;
    @Autowired
    private RestTemplate restTemplate;

    @Value("${spring.application.name}")
    private String appName;

    @GetMapping(value = "/test/app/name")
    public String test() {
        ServiceInstance serviceInstance = loadBalancerClient.choose("common-provider");
        String url = String.format("http://%s:%s/test/%s", serviceInstance.getHost(), serviceInstance.getPort(), appName);

        return restTemplate.getForObject(url, String.class);
    }

    @GetMapping(value = "/test/database/{table}")
    public Object testDataBase(@PathVariable String table, @PathParam("id") long id){
        ServiceInstance serviceInstance = loadBalancerClient.choose("database-query");
        String url = String.format("http://%s:%s/%s/getById?id=%s",serviceInstance.getHost()
        ,serviceInstance.getPort(), table, id);

        return restTemplate.getForObject(url, Object.class);
    }
}

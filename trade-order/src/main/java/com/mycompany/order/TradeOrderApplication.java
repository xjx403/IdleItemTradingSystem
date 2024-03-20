package com.mycompany.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @version 1.0
 * @Author xjx
 * @Date 2024/1/5 19:21
 * @注释
 */
@SpringBootApplication
@EnableDiscoveryClient
public class TradeOrderApplication {
    public static void main(String[] args) {
        SpringApplication.run(TradeOrderApplication.class);
    }
}

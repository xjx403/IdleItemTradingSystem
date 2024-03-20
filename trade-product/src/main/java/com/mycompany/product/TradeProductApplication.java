package com.mycompany.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @version 1.0
 * @Author xjx
 * @Date 2024/3/18 14:58
 * @注释
 */
@SpringBootApplication
@EnableDiscoveryClient
public class TradeProductApplication {
    public static void main(String[] args) {
        SpringApplication.run(TradeProductApplication.class);
    }
}

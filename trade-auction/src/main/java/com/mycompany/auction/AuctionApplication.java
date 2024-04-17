package com.mycompany.auction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @version 1.0
 * @Author xjx
 * @Date 2024/4/15 16:30
 * @注释
 */
@SpringBootApplication
@EnableDiscoveryClient
public class AuctionApplication {
    public static void main(String[] args) {
        SpringApplication.run(AuctionApplication.class);
    }
}

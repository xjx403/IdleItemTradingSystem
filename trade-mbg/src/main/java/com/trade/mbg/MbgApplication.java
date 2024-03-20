package com.trade.mbg;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @version 1.0
 * @Author xjx
 * @Date 2024/1/3 20:12
 * @注释
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.trade.mbg.mapper")
public class MbgApplication {
    public static void main(String[] args) {
        SpringApplication.run(MbgApplication.class);
    }
}

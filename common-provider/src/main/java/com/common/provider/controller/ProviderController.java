package com.common.provider.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @version 1.0
 * @Author xjx
 * @Date 2024/1/3 17:35
 * @注释
 */
@RestController
public class ProviderController {

    @Value("${server.port}")
    private String port;

    @GetMapping(value = "/test/{message}")
    public String test(@PathVariable String message){
        return "当前服务收到消息：" + message + ", 该服务由端口：" + port + "提供。";
    }
}

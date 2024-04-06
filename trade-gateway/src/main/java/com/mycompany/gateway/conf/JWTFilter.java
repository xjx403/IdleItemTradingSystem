package com.mycompany.gateway.conf;

import com.alibaba.nacos.api.utils.StringUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @version 1.0
 * @Author xjx
 * @Date 2024/4/2 16:38
 * @注释
 */
@Data
@Configuration
@Slf4j
public class JWTFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        String headerToken = request.getHeaders().getFirst("token");
        log.info("headToken:{}", headerToken);
        if (!StringUtils.isEmpty(headerToken)) {

        }
        return null;
    }

    @Override
    public int getOrder() {
        return 0;
    }
}

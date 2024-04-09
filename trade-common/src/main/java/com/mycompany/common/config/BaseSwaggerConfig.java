package com.mycompany.common.config;


import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;

/**
 * @version 1.0
 * @Author xjx
 * @Date 2024/1/5 18:48
 * @注释
 */
public abstract class BaseSwaggerConfig {
    private static final String visitUrl = "http://server:port/swagger-ui.html";

    @Bean
    public OpenAPI myOpenAPI(){
        return new OpenAPI()
                .info(new Info()
                    .title("校园闲置物品交易系统api")
                    .description("这是系统的前后端交互api")
                    .version("v0.1")
                    .contact(new Contact()
                        .name("xjx")
                        .email("2937882391@qq.com"))
                );
    }
}

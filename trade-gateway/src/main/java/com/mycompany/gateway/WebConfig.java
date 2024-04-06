package com.mycompany.gateway;

import com.mycompany.gateway.interceptor.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @version 1.0
 * @Author xjx
 * @Date 2024/4/2 18:42
 * @注释
 */
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**")   //默认对所有请求进行拦截
                .excludePathPatterns("/userLogin","/static/**");     //对login页面和静态资源不拦截
    }

}

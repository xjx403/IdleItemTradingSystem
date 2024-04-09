package com.mycompany.gateway.interceptor;

import com.mycompany.gateway.util.MyJWTUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @version 1.0
 * @Author xjx
 * @Date 2024/4/2 18:21
 * @注释
 */
//@Component
public class LoginInterceptor implements HandlerInterceptor {

//    private MyJWTUtil myJWTUtil = new MyJWTUtil();
//    private static String TOKEN_HEADER;
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        String token = request.getHeader("Authorization");
//        String JWT = token.substring(TOKEN_HEADER.length());
//        if (myJWTUtil.validateToken(token, null)) {
//            return true;
//        }
//        // 失败我们跳转回登录页面
//        request.setAttribute("msg","登录出错");
//        request.getRemoteHost();
//        request.getRequestDispatcher("/login").forward(request,response);
//        return false;
//    }
}

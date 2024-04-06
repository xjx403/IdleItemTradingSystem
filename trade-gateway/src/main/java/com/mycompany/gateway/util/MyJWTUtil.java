package com.mycompany.gateway.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @version 1.0
 * @Author xjx
 * @Date 2024/4/2 16:48
 * @注释
 */
public class MyJWTUtil {
    private final static Logger LOGGER = LoggerFactory.getLogger(MyJWTUtil.class);
    private final static String CLAIM_KEY_USERNAME = "sub";
    private final static String CLAIM_KEY_CREATED = "created";
//    private final static String CLAIM_KE
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private Long expiration;
    @Value("${jwt.tokenHead}")
    private String tokenHead;

    private static final long EXPIRATION = 1000 * 60 * 60;
    private static final String SECRET_KEY = "12313_";
    /**
     * 生成JWT的token
     */
    public static String generateToken(Map<String, Object>  claims) {
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .compact();
    }
    /**
     * 从token中获取JWT中的负载
     */

    public static Claims getClaimsFromToken(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .parseClaimsJws(token)
                    .getBody();
        }catch (Exception e) {
            LOGGER.info("获取token的负载失败:{}", token);
        }
        return claims;
    }
    /**
     * 生成token的过期时间
     */
    private static Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + EXPIRATION * 1000);
    }
    /**
     * 验证token是否还有效
     *
     * @param token       客户端传入的token
     * @param userDetails 从数据库中查询出来的用户信息
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        String username = getUsernameFromToken(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }
    /**
     * 从token中获取用户名
     */
    public String getUsernameFromToken(String token) {
        String username;
        try {
            Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        }catch (Exception e) {
            username = null;
        }
        return username;
    }


    /**
     * 从token中获取用户权限
     */
    public int getUserPrivilegeFromToken(String token) {
        int privilege;
        try {
            Claims claims = getClaimsFromToken(token);
            privilege = claims.get("privilege", Integer.class);
        }catch (Exception e) {
            privilege = 1;
        }

        return privilege;
    }
    /**
     * 验证token是否失效
     */

    private boolean isTokenExpired(String token) {
        Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    /**
     * 从token中获取过期时间
     */
    private Date getExpirationDateFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.getExpiration();
    }

    /**
     * 根据用户信息生成token
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
        claims.put(CLAIM_KEY_CREATED, new Date());

        return generateToken(claims);
    }

    /**
     * 当原来的token没过期时是可以刷新的
     *
     */
    public String refreshHeadToken(String oldToken) {
        if (StrUtil.isEmpty(oldToken)) {
            return null;
        }

        String token = oldToken.substring(tokenHead.length());
        if (StrUtil.isEmpty(token)) {
            return null;
        }

        //token校验不通过
        Claims claims = getClaimsFromToken(token);
        if (claims == null) {
            return null;
        }
        //过期不支持刷新
        if (isTokenExpired(token)) {
            return null;
        }

        //如果token在30min之内刷新过，就返回原token
        if (isTokenRefreshJustBefore(token, 30*60)) {
            return token;
        }else {
            claims.put(CLAIM_KEY_CREATED, new Date());
            return generateToken(claims);
        }
    }

    /**
     * 判断token在指定时间内是否刷新过
     * @param token 原token
     * @param time 指定时间（秒）
     */
    private boolean isTokenRefreshJustBefore(String token, int time) {
        Claims claims = getClaimsFromToken(token);
        Date created = claims.get(CLAIM_KEY_CREATED, Date.class);
        Date refreshDate = new Date();
        //刷新时间在创建时间内
        if (refreshDate.after(created) &&
                refreshDate.before(DateUtil.offsetSecond(created, time))) {
            return true;
        }

        return false;
    }
}


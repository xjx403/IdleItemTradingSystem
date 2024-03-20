package com.mycompany.common.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

/**
 * @version 1.0
 * @Author xjx
 * @Date 2024/3/18 21:05
 * @注释
 */
public class MyProductUtil {


    /**
     * 规则 生成日期 + 6位随机码
     * 后期可扩展为 生成日期 + 商品种类 + 所有者id + 随机码
     * @return
 generate    */
    static public String generateProductSn(){
        // 获取当前时间并格式化为YYYYMMDDHHMMSS
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String formattedTime = LocalDateTime.now().format(formatter);

        // 生成6位随机数字
        StringBuilder randomCode = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            randomCode.append(random.nextInt(10));
        }

        // 组合生成SN码
        return formattedTime + randomCode.toString();
    }
}

package com.mycompany.common.utils;

import sun.misc.BASE64Encoder;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @version 1.0
 * @Author xjx
 * @Date 2024/3/17 18:03
 * @注释
 */
public interface MyFileUtil {
    byte[] getBinary(File fileUrl) throws IOException;
    byte[] getBinary(InputStream ips, String fileType) throws IOException ;
    int save(File fileUrl) throws IOException ;
    int save(String saveWay) throws IOException ;
}

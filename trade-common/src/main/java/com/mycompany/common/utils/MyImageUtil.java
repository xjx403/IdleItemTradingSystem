package com.mycompany.common.utils;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * @version 1.0
 * @Author xjx
 * @Date 2024/3/17 18:08
 * @注释
 */
public class MyImageUtil implements MyFileUtil{
    static BASE64Encoder encoder = new BASE64Encoder();
    static BASE64Decoder decoder = new BASE64Decoder();
    static String defaultImageType = "jpg";

    String getFileTypeByUrl(String url){
        int lastIndexOfPoint = url.lastIndexOf('.');
        if (lastIndexOfPoint == -1) return null;
        return url.substring(lastIndexOfPoint + 1, url.length());
    }

    @Override
    public byte[] getBinary(File fileUrl) throws IOException {
        BufferedImage bi = ImageIO.read(fileUrl);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        String fileType = getFileTypeByUrl(fileUrl.toString());
        if (fileType == null || fileType.length() == 0) return null;
        ImageIO.write(bi, fileType, outputStream);
        return outputStream.toByteArray();
    }

    @Override
    public byte[] getBinary(InputStream ips, String fileType) throws IOException {
        BufferedImage bi = ImageIO.read(ips);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        ImageIO.write(bi, fileType, outputStream);
        return outputStream.toByteArray();
    }

    @Override
    public int save(File fileUrl) {
        return 0;
    }

    @Override
    public int save(String saveWay) {
        return 0;
    }


    public String imageToString(File file) throws IOException {
        byte[] bytes = getBinary(file);
        return encoder.encodeBuffer(bytes).trim();
    }
    public String imageToString(InputStream ips, String fileType) throws IOException {
        byte[] bytes = getBinary(ips, fileType);
        return encoder.encodeBuffer(bytes).trim();
    }
    public String imageToString(byte[] bytes, String fileType) throws IOException {
        return encoder.encodeBuffer(bytes).trim();
    }

    public byte[] stringToImage(String imageCodingString) throws IOException{
        return  decoder.decodeBuffer(imageCodingString);
    }
}

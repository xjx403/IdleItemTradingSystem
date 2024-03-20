package com.mycompany.common.api;

/**
 * @version 1.0
 * @Author xjx
 * @Date 2024/1/5 18:49
 * @注释
 */
public interface IErrorCode {
    /**
     * 返回码
     */
    long getCode();

    /**
     * 返回信息
     */
    String getMessage();
}

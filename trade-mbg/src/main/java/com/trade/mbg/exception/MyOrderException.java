package com.trade.mbg.exception;

/**
 * @version 1.0
 * @Author xjx
 * @Date 2024/4/7 16:46
 * @注释
 */
public class MyOrderException extends Exception{
    public MyOrderException(String message) {
        super(message);
    }

    public MyOrderException(String message, Throwable cause) {
        super(message, cause);
    }
}

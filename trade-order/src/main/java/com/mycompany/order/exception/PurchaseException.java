package com.mycompany.order.exception;

/**
 * @version 1.0
 * @Author xjx
 * @Date 2024/3/25 15:50
 * @注释
 */
public class PurchaseException extends Exception{
    public PurchaseException(String message) {
        super(message);
    }

    public PurchaseException(String message, Throwable cause) {
        super(message, cause);
    }
}

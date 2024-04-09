package com.mycompany.common.value_set;

/**
 * @version 1.0
 * @Author xjx
 * @Date 2024/4/6 21:00
 * @注释
 */
public enum OrderStatusCode {
    ;
    private int code;
    private String Message;

    OrderStatusCode(int code, String message) {
        this.code = code;
        Message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}

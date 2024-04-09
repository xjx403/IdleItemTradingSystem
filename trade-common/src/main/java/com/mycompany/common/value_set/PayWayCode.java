package com.mycompany.common.value_set;

/**
 * @version 1.0
 * @Author xjx
 * @Date 2024/4/6 20:53
 * @注释
 */
public enum PayWayCode {
    SELF_TRADING(1, "自行交易"),
    DEDUCTION_BALANCE(2, "扣除余额");
    private int code;
    private String message;

    PayWayCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

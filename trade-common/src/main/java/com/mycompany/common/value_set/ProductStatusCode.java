package com.mycompany.common.value_set;

/**
 * @version 1.0
 * @Author xjx
 * @Date 2024/3/18 19:38
 * @注释
 */
public enum ProductStatusCode {
    UNAUDITED("未审核", 0),
    LISTED("已上架", 1),
    SOLD("已售出", 2);

    private String status;
    private int code;
    private ProductStatusCode(String status, int code){
        this.status = status;
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}

package com.mycompany.common.value_set;

/**
 * @version 1.0
 * @Author xjx
 * @Date 2024/3/21 17:48
 * @注释
 */
public enum ImageTypeCode {
    PAYMENT_CODE("收款码", 1),
    PRODUCT_PICTURE("商品图", 2),
    OTHERS("其他",3);

    private String name;
    private int code;

    ImageTypeCode(String name, int code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}

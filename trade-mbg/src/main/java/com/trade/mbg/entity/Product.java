package com.trade.mbg.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author xjx
 * @since 2024-03-18 09:46:12
 */
@Getter
@Setter
@TableName("pms_product")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 出售者
     */
    private Long sellerId;

    /**
     * 商品唯一标识码
     */
    private String productSn;

    /**
     * 商品名称
     */
    private String productName;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 原价
     */
    private BigDecimal originPrice;

    /**
     * 商品状态
     */
    private Integer status;

    /**
     * 描述
     */
    private String description;

    /**
     * 商品图
     */
    private String picture;

    /**
     * 是否被删除
     */
    private Integer isDeleted;
}

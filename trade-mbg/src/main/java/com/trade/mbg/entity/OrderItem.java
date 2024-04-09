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
 * @since 2024-04-02 03:05:44
 */
@Getter
@Setter
@TableName("oms_order_item")
public class OrderItem implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 商品id
     */
    private Long productId;

    /**
     * 订单id
     */
    private Long orderId;

    /**
     * 商品纳入计算的最终价格
     */
    private BigDecimal endPrice;

    /**
     * 删除标志
     */
    private Integer isDeleted;

    @Override
    public String toString() {
        return "OrderItem{" +
                "id=" + id +
                ", productId=" + productId +
                ", orderId=" + orderId +
                ", endPrice=" + endPrice +
                ", isDeleted=" + isDeleted +
                '}';
    }
}

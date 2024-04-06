package com.trade.mbg.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
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
@TableName("oms_order")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 总金额
     */
    private BigDecimal totalAmount;

    /**
     * 支付方式，1-扣除用户余额；2-付款码转账
     */
    private Integer payWay;

    /**
     * 订单状态-待定
     */
    private Integer status;

    /**
     * 备注信息
     */
    private String remarks;

    /**
     * 删除标志
     */
    private Integer isDeleted;
}

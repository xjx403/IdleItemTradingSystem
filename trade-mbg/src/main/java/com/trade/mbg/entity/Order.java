package com.trade.mbg.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 
 * </p>
 *
 * @author xjx
 * @since 2024-01-07 06:59:48
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
    private LocalDate createTime;

    /**
     * 总金额
     */
    private Long totalAmount;

    /**
     * 支付状态
     */
    private Integer payStatus;

    /**
     * 订单状态-待定
     */
    private Integer status;

    /**
     * 备注信息
     */
    private String remarks;
}

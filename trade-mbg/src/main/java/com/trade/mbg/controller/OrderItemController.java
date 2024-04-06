package com.trade.mbg.controller;

import com.trade.mbg.entity.OrderItem;
import com.trade.mbg.service.OrderItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xjx
 * @since 2024-01-07 06:59:48
 */

@Tag(name = "OrderItemController",description = "主要用于订单商品项的增删查改")
@RestController
@RequestMapping("/orderItem")
public class OrderItemController {

    @Autowired
    public OrderItemService orderItemService;

    @Operation(description = "保存订单商品项")
    @PostMapping("/insert")
    public OrderItem insertOrderItem(OrderItem orderItem){
        boolean save = orderItemService.save(orderItem);
        if (!save || orderItem.getOrderId() == null) {
            return null;
        }

        return orderItem;
    }
}

package com.trade.mbg.controller;

import com.trade.mbg.entity.Order;
import com.trade.mbg.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
@Tag(name = "OrderController",description = "主要用于订单的增删查改")
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Operation(description = "用于保存订单")
    @PostMapping("/insert")
    public Order insertOrder(@RequestBody Order order){
        boolean save = orderService.save(order);
        if (!save || order.getId() == null) return null;
        return order;
    }
}

package com.mycompany.order.controller;

import com.mycompany.common.api.CommonResult;
import com.trade.mbg.entity.Order;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @version 1.0
 * @Author xjx
 * @Date 2024/1/5 19:29
 * @注释
 */
@Tag(name = "OrderController", description = "订单相关api")
@RestController
@RequestMapping("/order")
public class OrderController {
    @Operation(description = "获取用户的所有订单")
    @GetMapping(value = "/getMyOrders")
    public CommonResult getMyOrders(@Param("userId") Long userId){
        List<Order> orders = new ArrayList<>();
        orders.add(new Order());
        orders.add(new Order());
        return CommonResult.undeveloped(orders);
    }

    @Operation(description = "获取指定订单详情")
    @GetMapping(value = "/getById")
    public CommonResult getByOrderId(@Param("orderId") Long orderId){
        Order order = new Order();
        order.setId(orderId);
        return CommonResult.undeveloped(order);
    }
    @Operation(description = "为指定商品生成对应的订单，同时做交易货币的增减操作")
    @PostMapping(value = "/buy")
    public CommonResult buyProduct(Long productId){
        return CommonResult.undeveloped(new Order());
    }

    @Operation(description = "删除对应的订单信息")
    @PostMapping(value = "del")
    public CommonResult deleteOrderById(@Param("orderId") Long orderId) {
        return CommonResult.undeveloped();
    }

}

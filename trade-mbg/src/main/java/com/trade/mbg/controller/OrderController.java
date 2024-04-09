package com.trade.mbg.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mycompany.common.api.CommonResult;
import com.mycompany.common.value_set.PayWayCode;
import com.trade.mbg.entity.Order;
import com.trade.mbg.exception.MyOrderException;
import com.trade.mbg.service.OrderService;
import com.trade.mbg.service.MyOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xjx
 * @since 2024-01-07 06:59:48
 */
@Slf4j
@Tag(name = "OrderController",description = "主要用于订单的增删查改")
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    @Qualifier(value = "myOrderServiceImpl")
    private MyOrderService myOrderService;

    @Operation(description = "用于保存订单")
    @PostMapping("/insert")
    public Order insertOrder(@RequestBody Order order){
        if (order == null){
            log.warn("{} is null!!", order);
        }
        boolean save = orderService.save(order);
        if (!save || order.getId() == null) return null;
        return order;
    }

    @Operation(description = "查询某个用户的订单")
    @GetMapping("/getByUserId")
    public List<Order> getOrdersByUserId(@RequestParam Long userId) {
        if (userId == null) return null;
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id", userId);
        return orderService.list(wrapper);
    }

    @Operation(description = "根据订单ID查询指定订单")
    @GetMapping("/getById")
    public Order getOrderById(@RequestParam Long orderId) {
        if (orderId == null) return null;
        return orderService.getById(orderId);
    }

    @Operation(description = "修改")
    @PostMapping("/update")
    public Order updateOrder(@RequestBody Order order){
        boolean b = orderService.updateById(order);
        return  b ? order : null;
    }

    @Operation(description = "生成订单")
    @PostMapping("/generate")
    public Order generateOrder(@RequestParam Long userId,
                               @RequestParam Long productId,
                               @RequestParam Integer payWay,
                               @RequestParam(required = false) String remark) {

        if (productId == null || payWay == null || userId == null) return null;
        if (payWay != PayWayCode.SELF_TRADING.getCode()
                && payWay != PayWayCode.DEDUCTION_BALANCE.getCode())
            return null;

        Order order = null;
        try {
            order = myOrderService.createOrder(userId, productId, payWay, remark);
        } catch (MyOrderException e) {
            e.printStackTrace();
        }
        return order;
    }

    @Operation(description = "这是用于测试Spring事务的")
    @PostMapping("/test")
    public CommonResult testSpringTransactions(@RequestParam String remark) {
        Order order = null;
        try {
            order = myOrderService.transformTestByOrder(remark);
        } catch (MyOrderException e) {
            e.printStackTrace();
        }

        return order == null ? CommonResult.failed() : CommonResult.success(order);
    }

    @Operation(description = "删除订单信息")
    @GetMapping("/delete")
    public boolean deleteOrder(Long orderId){
        boolean end = false;
        try {
            end = myOrderService.delOrderById(orderId);
        } catch (MyOrderException e) {
            e.printStackTrace();
            return false;
        }

        return end;
    }

}

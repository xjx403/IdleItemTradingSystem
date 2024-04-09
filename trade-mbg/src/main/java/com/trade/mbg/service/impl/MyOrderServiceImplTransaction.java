package com.trade.mbg.service.impl;

import com.trade.mbg.entity.Order;
import com.trade.mbg.entity.OrderItem;
import com.trade.mbg.entity.Product;
import com.trade.mbg.exception.MyOrderException;
import com.trade.mbg.service.OrderItemService;
import com.trade.mbg.service.OrderService;
import com.trade.mbg.service.ProductService;
import com.trade.mbg.service.MyOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @version 1.0
 * @Author xjx
 * @Date 2024/4/7 17:54
 * @注释
 */
@Service
public class MyOrderServiceImplTransaction implements MyOrderService {
    @Autowired
    public OrderService orderService;
    @Autowired
    public OrderItemService orderItemService;
    @Autowired
    public ProductService productService;
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public Order createOrder(Long userId, List<Long> productIds, Integer payWay, String remark) throws MyOrderException {
        //创建订单
        Order order = new Order();
        order.setUserId(userId);
        order.setCreateTime(LocalDateTime.now());
        order.setPayWay(payWay == null ? 1 : payWay.intValue());
        order.setStatus(0);
        order.setIsDeleted(0);
        order.setRemarks(remark == null ? "这是默认备注" : remark);

        //保存订单
        orderService.save(order);
        if (order.getId() == null) {
            throw new MyOrderException("Order id is null!!");
        }
        // 计算总金额
        BigDecimal totalAmount = BigDecimal.ZERO;
        ArrayList<Product> products = new ArrayList<>(productIds.size());
        for (Long productId : productIds) {
            Product product = productService.getById(productId);
            if (product == null) {
                throw new MyOrderException("Product not found: " + productId);
            }
            products.add(product);
            totalAmount = totalAmount.add(product.getPrice());
        }
        order.setTotalAmount(totalAmount);

        //更新订单总金额
        orderService.updateById(order);
        for (Product product : products) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(order.getId());
            orderItem.setProductId(product.getId());
            orderItem.setEndPrice(product.getPrice());
            orderItem.setIsDeleted(0);
            //插入单个订单项
            orderItemService.save(orderItem);
        }

//        if (remark.equals("error happened")) {
//            throw new PurchaseException("事务回滚测试：订单生成回滚");
//        }
        return order;
    }

    @Override
    public Order createOrder(Long userId, Long productId, Integer payWay, String remark) throws MyOrderException {
        return null;
    }

    @Override
    public void payOrder(Long orderId) throws MyOrderException {

    }

    @Override
    public Order transformTestByOrder(String remark) throws MyOrderException {
        return null;
    }

    @Override
    public boolean delOrderById(Long orderId) throws MyOrderException {
        return false;
    }
}

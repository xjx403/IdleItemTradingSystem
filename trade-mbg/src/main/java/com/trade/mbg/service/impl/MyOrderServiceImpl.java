package com.trade.mbg.service.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.trade.mbg.entity.Order;
import com.trade.mbg.entity.OrderItem;
import com.trade.mbg.entity.Product;
import com.trade.mbg.exception.MyOrderException;
import com.trade.mbg.service.OrderItemService;
import com.trade.mbg.service.OrderService;
import com.trade.mbg.service.ProductService;
import com.trade.mbg.service.MyOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @version 1.0
 * @Author xjx
 * @Date 2024/4/7 16:47
 * @注释
 */
@Service
public class MyOrderServiceImpl implements MyOrderService {
    @Autowired
    public OrderService orderService;
    @Autowired
    public OrderItemService orderItemService;
    @Autowired
    public ProductService productService;
    @Autowired
    @Qualifier(value = "myOrderServiceImplTransaction")
    public MyOrderService myOrderServiceTransaction;

    @Override
    @Transactional(rollbackFor = Exception.class)
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
        return order;
    }

    /**
     *
     * @param userId
     * @param productId
     * @param payWay
     * @param remark
     * @return
     * @throws MyOrderException
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Order createOrder(Long userId, Long productId, Integer payWay, String remark) throws MyOrderException {
        ArrayList<Long> list = new ArrayList<>();
        list.add(productId);
        return myOrderServiceTransaction.createOrder(userId, list, payWay, remark);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Order createAuctionOrder(Long userId, Long productId, Integer payWay, String remark, BigDecimal needToPay) throws MyOrderException {
        return myOrderServiceTransaction.createAuctionOrder(userId, productId, payWay, remark, needToPay);
    }

    @Override
    public void payOrder(Long orderId) throws MyOrderException {
        Order order = orderService.getById(orderId);
        if (order.getPayWay() == 1) {
            //扣除余额，查看用户余额是否足够，足够则扣除，完成订单，不够则提醒用户余额不够。
        }else if (order.getPayWay() == 2) {
            //提醒卖家是否完成交易，完成则更改订单状态。
        }else {
            throw new MyOrderException("非法的付费方式：" + order.getPayWay());
        }
        return;
    }

    Order generateOrderForTest(String remark) {
        Order order = new Order();
        order.setUserId(1l);
        order.setCreateTime(LocalDateTime.now());
        order.setTotalAmount(new BigDecimal("5412.1"));
        order.setPayWay(new Integer(1));
        order.setIsDeleted(1);
        order.setStatus(0);
        order.setRemarks(remark);
        return order;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Order transformTestByOrder(String remark) throws MyOrderException {
        Order order = generateOrderForTest(remark);
        System.out.println( "orderId: " + order.getId());
        orderService.save(order);
        if (true) {
            throw new MyOrderException("这是用于测试的异常");
        }
        return order;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean delOrderById(Long orderId) throws MyOrderException {
        UpdateWrapper<OrderItem> itemUpdateWrapper = new UpdateWrapper<>();
        itemUpdateWrapper.set("is_deleted", 1);
        itemUpdateWrapper.eq("order_id", orderId);
        boolean remove = orderItemService.update(itemUpdateWrapper);
        if (!remove) {
            throw new MyOrderException("删除订单商品项错误");
        }
        UpdateWrapper<Order> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("is_deleted", 1);
        updateWrapper.eq("id", orderId);
        boolean orderRemove = orderService.update(updateWrapper);
        if (!orderRemove) {
            throw  new MyOrderException("删除订单错误");
        }
        return true;
    }


}

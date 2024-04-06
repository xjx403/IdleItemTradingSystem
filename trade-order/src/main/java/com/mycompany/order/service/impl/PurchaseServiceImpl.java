package com.mycompany.order.service.impl;

import com.mycompany.order.service.PurchaseService;
import com.mycompany.order.tmp.PurchaseException;
import com.trade.mbg.entity.Order;
import com.trade.mbg.entity.OrderItem;
import com.trade.mbg.entity.Product;
import com.trade.mbg.mapper.OrderMapper;
import com.trade.mbg.service.OrderItemService;
import com.trade.mbg.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @version 1.0
 * @Author xjx
 * @Date 2024/3/25 15:52
 * @注释
 */
public class PurchaseServiceImpl implements PurchaseService {
    @Autowired
    public RestTemplate restTemplate;
    @Value("${trade.host.url.mbg:#{null}}")
    private String mbgHostUrl;
    @Autowired
    public OrderService orderService;
    @Autowired
    public OrderItemService orderItemService;

    public Order postOrder(Order order){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Order> entity = new HttpEntity<>(order, headers);
        ResponseEntity<Order> responseEntity = restTemplate.postForEntity(mbgHostUrl + "/order/insert", entity, Order.class);
        return responseEntity.getBody();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createOrder(Long userId, List<Long> productIds, Integer payWay) throws PurchaseException {
        //创建订单
        Order order = new Order();
        order.setUserId(userId);
        order.setCreateTime(LocalDateTime.now());
        order.setPayWay(payWay == null ? 1 : payWay.intValue());
        order.setStatus(0);
//        order.setRemarks();
        boolean save = orderService.save(order);
        if (!save) {
            throw new PurchaseException("Order insert failed:" + save);
        }
        if (order.getId() == null) {
            throw new PurchaseException("Order id is null!!");
        }
        // 计算总金额
        BigDecimal totalAmount = BigDecimal.ZERO;
        ArrayList<Product> products = new ArrayList<>(productIds.size());
        for (Long productId : productIds) {
            Product product = restTemplate.getForObject(mbgHostUrl + "/product/select?id=" + productId, Product.class);
            if (product == null) {
                throw new PurchaseException("Product not found: " + productId);
            }
            products.add(product);
            totalAmount = totalAmount.add(product.getPrice());
        }
        order.setTotalAmount(totalAmount);

        for (Product product : products) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(order.getId());
            orderItem.setProductId(product.getId());
            orderItem.setEndPrice(product.getPrice());
            orderItemService.save(orderItem);
        }
        return order.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void payOrder(Long orderId) throws PurchaseException {
        Order order = orderService.getById(orderId);
        if (order.getPayWay() == 1) {
            //扣除余额，查看用户余额是否足够，足够则扣除，完成订单，不够则提醒用户余额不够。
        }else if (order.getPayWay() == 2) {
            //提醒卖家是否完成交易，完成则更改订单状态。
        }else {
            throw new PurchaseException("非法的付费方式：" + order.getPayWay());
        }
        return;
    }
}

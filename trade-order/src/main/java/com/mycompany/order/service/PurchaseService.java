package com.mycompany.order.service;

import com.mycompany.order.tmp.PurchaseException;

import java.util.List;

/**
 * @version 1.0
 * @Author xjx
 * @Date 2024/3/25 15:49
 * @注释
 */
public interface PurchaseService {
    Long createOrder(Long userId, List<Long> productIds, Integer payWay) throws PurchaseException;

    void payOrder(Long orderId) throws PurchaseException;
}

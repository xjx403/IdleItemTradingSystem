package com.mycompany.order.service;

import com.mycompany.order.exception.PurchaseException;
import com.trade.mbg.entity.Order;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @version 1.0
 * @Author xjx
 * @Date 2024/3/25 15:49
 * @注释
 */
@Service
public interface PurchaseService {
    Order createOrder(Long userId, List<Long> productIds, Integer payWay, String remark) throws PurchaseException;
    Order createOrder(Long userId, Long productId, Integer payWay, String remark) throws PurchaseException;
    void payOrder(Long orderId) throws PurchaseException;
}

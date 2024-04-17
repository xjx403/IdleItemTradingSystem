package com.trade.mbg.service;

import com.trade.mbg.entity.Order;
import com.trade.mbg.exception.MyOrderException;

import java.math.BigDecimal;
import java.util.List;

/**
 * @version 1.0
 * @Author xjx
 * @Date 2024/4/7 16:45
 * @注释
 */
public interface MyOrderService {
    Order createOrder(Long userId, List<Long> productIds, Integer payWay, String remark) throws MyOrderException;
    Order createOrder(Long userId, Long productId, Integer payWay, String remark) throws MyOrderException;
    Order createAuctionOrder(Long userId, Long productId, Integer payWay, String remark, BigDecimal needToPay) throws MyOrderException;
    void payOrder(Long orderId) throws MyOrderException;

    Order transformTestByOrder(String remark) throws MyOrderException;

    boolean delOrderById(Long orderId) throws MyOrderException;
}
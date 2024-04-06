package com.trade.mbg.service.impl;

import com.trade.mbg.entity.Order;
import com.trade.mbg.mapper.OrderMapper;
import com.trade.mbg.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xjx
 * @since 2024-04-02 03:05:44
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

}

package com.trade.mbg.service.impl;

import com.trade.mbg.entity.Product;
import com.trade.mbg.mapper.ProductMapper;
import com.trade.mbg.service.ProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xjx
 * @since 2024-03-18 09:46:12
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {

}

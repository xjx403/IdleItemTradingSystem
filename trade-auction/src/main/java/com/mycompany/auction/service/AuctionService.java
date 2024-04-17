package com.mycompany.auction.service;

import com.github.pagehelper.PageInfo;
import com.mycompany.auction.entity.AuctionProductVO;
import com.trade.mbg.entity.Product;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @version 1.0
 * @Author xjx
 * @Date 2024/4/15 16:53
 * @注释
 */
public interface AuctionService {
    AuctionProductVO getAuctionProductById(long productId);
    AuctionProductVO setAuctionProduct(long productId, BigDecimal miniIncrement, LocalDateTime endTime);
    List<AuctionProductVO> list();
    PageInfo<AuctionProductVO> list(Integer pageNumber, Integer pageSize);

    /**
     * 用户进行竞拍出价
     * @param userId
     * @param productId
     * @param increment 加价幅度
     * @return 出价结果
     */
    AuctionProductVO bid(Long userId, Long productId, BigDecimal increment);

    /**
     * 用户报名参加某拍卖商品的竞拍
     * 日后可再次添加用户的权限验证，保证金验证等
     * 目前直接同意用户的报名
     * @param userId
     * @param productId
     * @return 报名结果 重复报名返回false
     */
    boolean signUp(Long userId, Long productId);
}

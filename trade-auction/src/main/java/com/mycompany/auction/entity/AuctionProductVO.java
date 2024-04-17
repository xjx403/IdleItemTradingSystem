package com.mycompany.auction.entity;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import com.trade.mbg.entity.Product;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * @version 1.0
 * @Author xjx
 * @Date 2024/4/15 16:35
 * @注释
 */
@Data
public class AuctionProductVO implements Serializable {
    private Long productId;
    private String productName;
    private String picture;
    private BigDecimal currentPrice;
    private BigDecimal startPrice;
    private BigDecimal minBidIncrement;
    private LocalDateTime endTime;
    private List<String> bidHistory;

    public AuctionProductVO(){

    }
    public AuctionProductVO(Product product) {
        this.productId = product.getId();
        this.productName = product.getProductName();
        this.picture = product.getPicture();
        this.currentPrice = product.getPrice();
        this.startPrice = product.getPrice();
        this.minBidIncrement = new BigDecimal("10");
        this.endTime = LocalDateTimeUtil.offset(LocalDateTime.now(), 5, ChronoUnit.HOURS);
        this.bidHistory = null;
    }

    public AuctionProductVO(Product product,
                            BigDecimal startPrice,
                            BigDecimal minBidIncrement,
                            LocalDateTime endTime) {
        this.productId = product.getId();
        this.productName = product.getProductName();
        this.picture = product.getPicture();
        this.currentPrice = startPrice;
        this.startPrice = startPrice;
        this.minBidIncrement = minBidIncrement;
        this.endTime = endTime;
        this.bidHistory = null;
    }
}

package com.mycompany.auction;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.mycompany.auction.service.AuctionService;
import com.mycompany.auction.service.AuctionTaskService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

/**
 * @version 1.0
 * @Author xjx
 * @Date 2024/4/15 17:02
 * @注释
 */
public class AuctionServiceTest extends BaseSpringBootTest{

    @Autowired
    private AuctionService auctionService;
    @Autowired
    private AuctionTaskService auctionTaskService;

    @Test
    public void TestGetAuctionProduct(){
        for (int i = 1; i < 10; i++) {
            System.out.println(auctionService.getAuctionProductById(i));
        }
    }

    @Test
    public void  TestList(){
        System.out.println(auctionService.list());
    }

    @Test
    public void TestSetAuctionProduct(){
        LocalDateTime endTime = LocalDateTimeUtil.offset(LocalDateTime.now(), 5, ChronoUnit.HOURS);
        for (int i = 1; i < 10; i++) {
            System.out.println(auctionService.setAuctionProduct(i, new BigDecimal(100), endTime));
        }
    }
}

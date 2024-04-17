package com.mycompany.auction;

import com.mycompany.auction.service.AuctionService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @version 1.0
 * @Author xjx
 * @Date 2024/4/15 17:02
 * @注释
 */
public class AuctionServiceTest extends BaseSpringBootTest{

    @Autowired
    private AuctionService auctionService;

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
}

package com.mycompany.auction.controller;

import cn.hutool.core.date.LocalDateTimeUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mycompany.auction.entity.AuctionProductVO;
import com.mycompany.auction.service.AuctionService;
import com.mycompany.auction.service.AuctionTaskService;
import com.mycompany.common.api.CommonResult;
import com.trade.mbg.entity.Product;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @version 1.0
 * @Author xjx
 * @Date 2024/4/15 16:49
 * @注释
 */
@Tag(name = "AuctionController", description = "拍卖相关的API接口")
@RestController
@RequestMapping("/auction")
public class AuctionController {

    @Autowired
    private AuctionService auctionService;
    @Autowired
    private AuctionTaskService auctionTaskService;
    @Autowired
    private RestTemplate restTemplate;
    @Value("${trade.host.url.mbg}")
    private String mbgHost;

    private AuctionProductVO setFakeBiddingHistory(AuctionProductVO auctionProduct){
        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            BigDecimal add = auctionProduct.getStartPrice().add(auctionProduct.getMinBidIncrement().multiply(new BigDecimal(i)));
            list.add("User" + i +" 出价 $" + add);
        }
        auctionProduct.setBidHistory(list);
        return auctionProduct;
    }

    @Operation(description = "返回指定拍卖商品的信息")
    @GetMapping("/details")
    public CommonResult getAuctionDetails(long productId) {
        AuctionProductVO auctionProduct = auctionService.getAuctionProductById(productId);
        if (auctionProduct == null) {
            return CommonResult.failed("未查询到结果");
        }

        //auctionProduct = setFakeBiddingHistory(auctionProduct);
        return CommonResult.success(auctionProduct);
    }

    @Operation(description = "获取正在拍卖的商品列表")
    @GetMapping("/list")
    public CommonResult listAuctionProducts(Integer pageNumber, Integer pageSize){
        PageInfo<AuctionProductVO> pageInfo = auctionService.list(pageNumber, pageSize);
        if (pageInfo == null || pageInfo.getSize() == 0) return CommonResult.validateFailed("未查询到参数");
        return CommonResult.success(pageInfo);
    }

    @Operation(description = "列出所有拍卖商品")
    @GetMapping("/listAll")
    public CommonResult listAllAuctionProducts(){
        List<AuctionProductVO> auctionProductVOS = auctionService.list();
        if (auctionProductVOS == null || auctionProductVOS.size() == 0) return CommonResult.validateFailed("未查询到参数");
        return CommonResult.success(auctionProductVOS);
    }

    @Operation(description = "对拍卖商品出价")
    @PostMapping("/bid")
    public CommonResult bidProduct(@RequestParam Long userId,
                                   @RequestParam Long productId,
                                   @RequestParam BigDecimal increment){
        AuctionProductVO productVO = auctionService.bid(userId, productId, increment);
        if (productVO == null) {
            return CommonResult.failed("出价失败");
        }
            return CommonResult.success(productVO);
    }

    @Operation(description = "报名参加某件商品的拍卖")
    @PostMapping("/signUp")
    public CommonResult signUpForAuction(@RequestParam Long userId,
                                         @RequestParam Long productId) {

        boolean signUpEnd = auctionService.signUp(userId, productId);
        if (!signUpEnd) {
            return CommonResult.failed("用户" + userId +" 不能重复报名");
        }
        return CommonResult.success("用户" + userId + " 报名成功！");
    }


    @Operation(description = "将某件商品上架拍卖")
    @PostMapping("/changeToAuction")
    public CommonResult changeProductToAuctionStatus(@RequestParam Long userId,
                                                     @RequestParam Long productId,
                                                     @RequestParam(required = false) Long duration,
                                                     @RequestParam(required = false) BigDecimal miniIncrement) {
        //参数校验
        Product product = restTemplate.getForObject(mbgHost + "/product/select?id=" + productId, Product.class);
        if (product.getSellerId() != userId) return CommonResult.validateFailed("该用户不是商品的卖家，无权进行该操作！");

        LocalDateTime endTime = LocalDateTimeUtil.offset(LocalDateTime.now(), duration, ChronoUnit.SECONDS);
        AuctionProductVO productVO = auctionService.setAuctionProduct(productId, miniIncrement, endTime);
        if (productVO == null) {
            return CommonResult.failed();
        }
        auctionTaskService.startTask(productVO);
        return CommonResult.success(productVO);
//        HashMap<String, Object> map = new HashMap<>();
//        map.put("userId", userId);
//        map.put("productId", productId);
//        map.put("duration", duration);
//        map.put("miniIncre", miniIncrement);
//        return CommonResult.success(map);
    }
}

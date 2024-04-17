package com.mycompany.auction.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mycompany.auction.entity.AuctionProductVO;
import com.mycompany.auction.service.AuctionService;
import com.mycompany.common.api.CommonResult;
import com.trade.mbg.entity.Product;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
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
}

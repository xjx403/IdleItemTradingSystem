package com.mycompany.order.controller;

import com.mycompany.common.api.CommonResult;
import com.mycompany.common.value_set.PayWayCode;
import com.mycompany.order.service.PurchaseService;
import com.mycompany.order.exception.PurchaseException;
import com.trade.mbg.entity.Order;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * @version 1.0
 * @Author xjx
 * @Date 2024/1/5 19:29
 * @注释
 */
@Tag(name = "OrderController", description = "订单相关api")
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private PurchaseService purchaseService;

    @Value("${trade.host.url.mbg:#{null}}")
    private String mbgHostUrl;

    @Operation(description = "获取用户的所有订单")
    @GetMapping(value = "/getMyOrders")
    public CommonResult getMyOrders(@Param("userId") Long userId){
        List<Order> orders = restTemplate.getForObject(mbgHostUrl + "/order/getByUserId?userId=" + userId, List.class);
        return CommonResult.success(orders);
    }

    @Operation(description = "获取指定订单详情")
    @GetMapping(value = "/getById")
    public CommonResult getByOrderId(@RequestParam("orderId") Long orderId){
        Order order = restTemplate.getForObject(mbgHostUrl + "/order/getById?userId=" + orderId, Order.class);
        if (order == null) return CommonResult.failed("未查询到！");
        return CommonResult.success(order);
    }

//    public Order postOrder(Order order){
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//        HttpEntity<Order> entity = new HttpEntity<>(order, headers);
//        System.out.println(entity);
//        ResponseEntity<Order> responseEntity = restTemplate.postForEntity(mbgHostUrl + "/order/insert", entity, Order.class);
//        return responseEntity.getBody();
//    }

    @Operation(description = "为指定商品生成对应的订单")
    @PostMapping(value = "/buy")
    public CommonResult buyProduct(@RequestParam Long userId,
                                   @RequestParam Long productId,
                                   @RequestParam Integer payWay,
                                   @RequestParam(required = false) String remark ){
        if (productId == null || payWay == null || userId == null) return CommonResult.validateFailed();
        if (payWay != PayWayCode.SELF_TRADING.getCode()
                && payWay != PayWayCode.DEDUCTION_BALANCE.getCode())
            return CommonResult.validateFailed();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> valueMap = new LinkedMultiValueMap<>();
        valueMap.add("userId", userId.toString());
        valueMap.add("productId", productId.toString());
        valueMap.add("payWay", payWay.toString());

        valueMap.add("remark", remark);
        HttpEntity<MultiValueMap> entity = new HttpEntity<>(valueMap, headers);
        ResponseEntity<Order> responseEntity = restTemplate.postForEntity(mbgHostUrl + "/order/generate", entity, Order.class);
        Order order = responseEntity.getBody();
        return order == null ? CommonResult.failed("订单生成失败！") : CommonResult.success(order);
    }

    @Operation(description = "删除对应的订单信息")
    @PostMapping(value = "/del")
    public CommonResult deleteOrderById(@Param("orderId") Long orderId) {
        Boolean delEnd = restTemplate.getForObject(mbgHostUrl + "/order/delete?orderId=" + orderId, Boolean.class);
        return delEnd ? CommonResult.success("删除成功") : CommonResult.failed("删除失败");
    }

}

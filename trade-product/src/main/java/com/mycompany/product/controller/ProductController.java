package com.mycompany.product.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mycompany.common.api.CommonResult;
import com.mycompany.common.utils.MyProductUtil;
import com.mycompany.common.value_set.ProductStatusCode;
import com.trade.mbg.entity.Product;
import com.trade.mbg.mapper.ProductMapper;
import com.trade.mbg.service.ProductService;
import com.trade.mbg.service.impl.ProductServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @version 1.0
 * @Author xjx
 * @Date 2024/3/18 14:59
 * @注释
 */

@RestController
@RequestMapping("/product")
public class ProductController {

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);
    @Value("${trade.host.url.mbg:#{null}}")
    private String mbgHostUrl;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping(value = "/list")
    public CommonResult getProducts(Integer pageNumber, Integer pageSize){
        if (mbgHostUrl == null) {
            log.warn("mbgHostUrl == null !! 实体查询模块失联");
            return CommonResult.failed();
        }
        return restTemplate.getForObject(mbgHostUrl + "/product/list?pageNumber="+ pageNumber + "&pageSize=" + pageSize, CommonResult.class);
    }

    @PostMapping(value = "/add")
    public CommonResult addNewProduct(  @RequestParam Long sellerId,
                                        @RequestParam String productName,
                                        @RequestParam BigDecimal productPrice,
                                        @RequestParam(required = false) BigDecimal originPrice,
                                        @RequestParam(required = false) String description){
        if (mbgHostUrl == null) {
            log.warn("mbgHostUrl == null !! 实体查询模块失联");
            return CommonResult.failed();
        }
        if (originPrice == null) originPrice = productPrice;
        if (description == null) description = "所有者暂未提供该商品的描述";
        Product product = new Product();
        product.setSellerId(sellerId);
        product.setProductSn(MyProductUtil.generateProductSn());
        product.setPrice(productPrice);
        product.setOriginPrice(originPrice);
        product.setProductName(productName);
        product.setStatus(ProductStatusCode.UNAUDITED.getCode());
        product.setDescription(description);
        product.setIsDeleted(0);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Product> entity = new HttpEntity<>(product, headers);
        ResponseEntity<Product> responseEntity = restTemplate.postForEntity(mbgHostUrl + "/product/insert", entity, Product.class);
        Product endProduct = responseEntity.getBody();
        if (endProduct == null) {
            return CommonResult.failed();
        }
        log.info(String.valueOf(endProduct));
        return CommonResult.success(endProduct);
    }


    @PostMapping(value = "/change")
    public CommonResult changeProductInfo(@RequestBody Product product){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Product> entity = new HttpEntity<>(product, headers);
        ResponseEntity<Boolean> responseEntity = restTemplate.postForEntity(mbgHostUrl + "/product/update", entity, Boolean.class);
        return responseEntity.getBody() ? CommonResult.success("修改成功") : CommonResult.failed();
    }

    @PostMapping(value = "/change/uploadImage")
    public CommonResult uploadPicture(MultipartFile file){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<MultipartFile> entity = new HttpEntity<>(file, headers);
        ResponseEntity<Boolean> responseEntity = restTemplate.postForEntity(mbgHostUrl + "/picture/upload", entity, Boolean.class);
        return responseEntity.getBody() ? CommonResult.success("上传成功") : CommonResult.failed("上传失败");
    }


    @GetMapping(value = "/get/BySn")
    public CommonResult getProductBySN(String sn){
        Map<String, String> eqMap = new HashMap<>();
        eqMap.put("product_sn", sn);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Map> entity = new HttpEntity<>(eqMap, headers);
        ResponseEntity<Product> responseEntity = restTemplate.postForEntity(mbgHostUrl + "/product/selectByWrapper", entity, Product.class);
        Product product = responseEntity.getBody();
        return product == null ? CommonResult.failed() : CommonResult.success(product);
    }

    // GET http://localhost:8092/product/select
    @GetMapping(value = "/get/ById")
    public CommonResult getProductById(long id) {
        Product product = restTemplate.getForObject(mbgHostUrl + "/product/select?id=" + id, Product.class);
        return product == null ? CommonResult.failed() : CommonResult.success(product);
    }
}

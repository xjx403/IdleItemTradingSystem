package com.trade.mbg.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mycompany.common.api.CommonResult;
import com.trade.mbg.entity.Member;
import com.trade.mbg.entity.Product;
import com.trade.mbg.service.MemberService;
import com.trade.mbg.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

/**
 * @author xjx
 * @since 2024-01-03 09:36:52
 */
@Tag(name = "ProductController", description = "商品信息的增删查改")
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private MemberService memberService;
    @Autowired
    private ProductService productService;

    @Operation(description = "插入商品")
    @PostMapping(value = "/insert")
    public Product insertProduct( @RequestBody Product product){
        if (memberService.getById(product.getSellerId()) == null) {
            return null;
        }
        boolean saveEnd = productService.save(product);
        return saveEnd ? product : null;
    }

    @Operation(description = "通过ID查看商品信息")
    @GetMapping(value = "/select")
    public Product selectProductById(long id){
        Product product = productService.getById(id);
        return product;
    }
    @Operation(description = "通过查询参数查看单个商品信息")
    @PostMapping(value = "/selectByWrapper")
    public Product selectProduct(@RequestBody Map<String, String> eqMap){
        QueryWrapper<Product> wrapper = new QueryWrapper<>();
        for (String columnName: eqMap.keySet()) {
            wrapper.eq(columnName, eqMap.get(columnName));
        }
        return productService.getOne(wrapper);
    }

    @Operation(description = "修改商品信息")
    @PostMapping(value = "/update")
    public boolean updateProductInfo(@RequestBody Product product) {
        return productService.updateById(product);
    }

    @Operation(description = "分页展示商品")
    @GetMapping(value = "/list")
    public CommonResult getProducts(Integer pageNumber, Integer pageSize){

        /*从数据库查询数据，来进行分页*/
        PageHelper.startPage(pageNumber, pageSize);
        List<Product> list = productService.list();
        PageInfo<Product> productPageInfo = new PageInfo<>(list);
        List<Product> products = productPageInfo.getList();
        for (Product product : products) {
            Member seller = memberService.getById(product.getSellerId());
            product.setDescription(seller.getUsername());
        }
        return CommonResult.success(productPageInfo);
    }

}

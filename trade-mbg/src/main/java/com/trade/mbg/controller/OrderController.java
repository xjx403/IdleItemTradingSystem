package com.trade.mbg.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xjx
 * @since 2024-01-07 06:59:48
 */
@Tag(name = "OrderController",description = "主要用于订单的增删查改")
@Controller
@RequestMapping("/order")
public class OrderController {

}

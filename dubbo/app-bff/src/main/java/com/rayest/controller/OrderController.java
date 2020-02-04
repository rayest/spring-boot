package com.rayest.controller;

import com.rayest.api.model.Order;
import com.rayest.service.OrderControllerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class OrderController {

    @Resource
    private OrderControllerService orderControllerService;

    @GetMapping("/order/{orderNo}")
    public Order getByOrderNo(@PathVariable("orderNo") String orderNo){
        return orderControllerService.getByOrderNo(orderNo);
    }
}

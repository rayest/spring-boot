package com.rayest.controller;

import com.rayest.service.OrderService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class OrderController {

    @Resource
    private OrderService orderService;

    @PostMapping("/order/init/{userId}")
    public void initOrder(@PathVariable("userId") String userId){
        orderService.initOrder(userId);
    }
}

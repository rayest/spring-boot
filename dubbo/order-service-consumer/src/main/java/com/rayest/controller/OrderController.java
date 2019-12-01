package com.rayest.controller;

import com.rayest.model.Order;
import com.rayest.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class OrderController {

    @Resource
    private OrderService orderService;

    @GetMapping("/order/{userId}")
    public Order getByUserId(@PathVariable("userId") String userId){
        return orderService.getByUserId(userId);
    }
}

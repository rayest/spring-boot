package com.rayest.controller;

import com.rayest.model.Order;
import com.rayest.service.OrderControllerService;
import com.rayest.service.OrderService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class OrderController {

    @Resource
    private OrderControllerService orderControllerService;

    @GetMapping("/order/{orderNo}")
    public Order getByOrderNo(@PathVariable("orderNo") String orderNo){
        return orderControllerService.getByOrderNo(orderNo);
    }

    @GetMapping("/orders/{userNo}")
    public List<Order> getByUserNo(@PathVariable("userNo") String userNo){
        return orderControllerService.getByUserNo(userNo);
    }
}

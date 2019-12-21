package com.rayest.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.rayest.model.Order;
import com.rayest.model.User;
import com.rayest.service.OrderControllerService;
import com.rayest.service.OrderService;
import com.rayest.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class OrderControllerServiceImpl implements OrderControllerService {


    @Reference
    private OrderService orderService;

    @Override
    public Order getByOrderNo(String orderNo) {
        return null;
    }

    @Override
    public List<Order> getByUserNo(String userNo) {
        return orderService.getByUserNo(userNo);
    }
}

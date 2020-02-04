package com.rayest.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.rayest.model.Order;
import com.rayest.service.OrderControllerService;
import com.rayest.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class OrderControllerServiceImpl implements OrderControllerService {

//    @Reference(version = "1.0-SNAPSHOT", loadbalance = "roundrobin", actives = 10)
    @Reference(loadbalance = "demo")
    private OrderService orderService;

    @Override
    public Order getByOrderNo(String orderNo) {
        return null;
    }

    @HystrixCommand(fallbackMethod = "getByUserNoDefault")
    @Override
    public List<Order> getByUserNo(String userNo) {
        return orderService.getByUserNo(userNo);
    }

    public List<Order> getByUserNoDefault(String userNo){
        log.info("order 服务不可用，返回空集合. userNo: {}", userNo);
        List<Order> orders = new ArrayList<>();
        orders.add(new Order().setId(-1).setUserNo("-1"));
        return orders;
    }
}

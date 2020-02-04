package com.rayest.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.rayest.api.model.Order;
import com.rayest.service.OrderControllerService;
import com.rayest.api.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderControllerServiceImpl implements OrderControllerService {

//    @Reference(version = "1.0-SNAPSHOT", loadbalance = "roundrobin", actives = 10)
    @Reference(loadbalance = "demo")
    private OrderService orderService;

    @Override
    @HystrixCommand(fallbackMethod = "getByOrderNoDefault")
    public Order getByOrderNo(String orderNo) {
        return orderService.getByOrderNo(orderNo);
    }

    public Order getByOrderNoDefault(String orderNo){
        log.info("order 服务不可用，返回默认集合. orderNo: {}", orderNo);
        return new Order().setId(-1).setUserNo("-1").setOrderNo(orderNo);
    }
}

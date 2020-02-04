package com.rayest.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.rayest.api.service.OrderService;
import com.rayest.entity.OrderEntity;
import com.rayest.mapper.OrderMapper;
import com.rayest.api.model.Order;
import com.rayest.persistence.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Service
@Component
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderRepository orderRepository;

    @Override
    public Order getByOrderNo(String orderNo) {
        OrderEntity orderEntity = orderRepository.selectByOrderNo(orderNo);
        return OrderMapper.INSTANCE.toOrder(orderEntity);
    }
}

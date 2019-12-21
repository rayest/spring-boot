package com.rayest.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.rayest.entity.OrderEntity;
import com.rayest.mapper.OrderMapper;
import com.rayest.persistence.OrderRepository;
import com.rayest.model.Order;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

@Service
@Component
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderRepository orderRepository;

    @Override
    public void initOrder(String s) {

    }

    @Override
    public List<Order> getByUserNo(String userNo) {
        List<OrderEntity> orderEntities = orderRepository.selectByUserNo(userNo);
        return OrderMapper.INSTANCE.toOrderList(orderEntities);
    }
}

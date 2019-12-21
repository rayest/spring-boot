package com.rayest.service;

import com.rayest.model.Order;

import java.util.List;

public interface OrderService {
    void initOrder(String userNo);
    List<Order> getByUserNo(String userNo);
}

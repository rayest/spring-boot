package com.rayest.service;

import com.rayest.model.Order;

import java.util.List;

public interface OrderControllerService {
    Order getByOrderNo(String orderNo);

    List<Order> getByUserNo(String userNo);
}

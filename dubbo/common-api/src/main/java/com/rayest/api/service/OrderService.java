package com.rayest.api.service;

import com.rayest.api.model.Order;

public interface OrderService {
    Order getByOrderNo(String orderNo);
}

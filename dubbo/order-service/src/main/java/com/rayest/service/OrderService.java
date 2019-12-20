package com.rayest.service;

import com.rayest.model.Order;

public interface OrderService {
    Order getByUserId(String userId);
}

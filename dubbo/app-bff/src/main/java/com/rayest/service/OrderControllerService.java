package com.rayest.service;


import com.rayest.api.model.Order;

public interface OrderControllerService {
    Order getByOrderNo(String orderNo);
}

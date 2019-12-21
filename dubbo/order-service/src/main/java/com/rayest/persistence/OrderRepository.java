package com.rayest.persistence;

import com.rayest.entity.OrderEntity;

import java.util.List;

public interface OrderRepository {
    List<OrderEntity> selectByUserNo(String userNo);
}

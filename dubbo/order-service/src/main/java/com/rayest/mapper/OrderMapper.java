package com.rayest.mapper;

import com.rayest.entity.OrderEntity;
import com.rayest.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    List<Order> toOrderList(List<OrderEntity> orderEntities);

}

package com.rayest.persistence;

import com.rayest.entity.OrderEntity;
import org.apache.ibatis.annotations.Select;

public interface OrderRepository {

    @Select("select * from t_order where order_no = #{orderNo}")
    OrderEntity selectByOrderNo(String orderNo);
}

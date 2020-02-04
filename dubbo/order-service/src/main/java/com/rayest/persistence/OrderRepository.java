package com.rayest.persistence;

import com.rayest.entity.OrderEntity;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface OrderRepository {
    @Select("select * from t_order where user_no = #{userNo}")
    List<OrderEntity> selectByUserNo(String userNo);
}

package com.rayest.service.impl;

import com.rayest.model.UserAddress;
import com.rayest.service.OrderService;
import com.rayest.service.UserService;

import java.util.List;

public class OrderServiceImpl implements OrderService {

    // dubbo 面向接口编程。此处的 userService 可能其他服务器
    private UserService userService;

    public void initOrder(String userId) {
        // 远程调用
        List<UserAddress> userAddressList = userService.getByUserId(userId);
        System.out.println(userAddressList);
    }
}

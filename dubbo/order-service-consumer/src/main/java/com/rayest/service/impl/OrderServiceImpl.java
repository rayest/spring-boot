package com.rayest.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.rayest.model.UserAddress;
import com.rayest.service.OrderService;
import com.rayest.service.UserService;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    // dubbo 面向接口编程。此处的 userService 可能其他服务器
    @Resource
    private UserService userService;

    public void initOrder(String userId) {
        log.info("userId: {}", userId);
        // 远程调用
        List<UserAddress> userAddressList = userService.getByUserId(userId);
        System.out.println(userAddressList);
    }
}

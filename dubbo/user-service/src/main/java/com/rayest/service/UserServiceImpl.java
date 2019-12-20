package com.rayest.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.rayest.model.UserAddress;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Service
@Component
public class UserServiceImpl implements UserService {
    public List<UserAddress> getByUserId(String userId) {
        UserAddress userAddress1 = new UserAddress().setUserId("1").setAddress("上海");
        UserAddress userAddress2 = new UserAddress().setUserId("1").setAddress("杭州");
        return Arrays.asList(userAddress1, userAddress2);
    }
}

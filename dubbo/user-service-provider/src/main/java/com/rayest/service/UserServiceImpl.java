package com.rayest.service;

import com.rayest.model.UserAddress;

import java.util.Arrays;
import java.util.List;

public class UserServiceImpl implements UserService {
    public List<UserAddress> getByUserId(String userId) {
        UserAddress userAddress1 = new UserAddress().setUserId("1").setAddress("上海");
        UserAddress userAddress2 = new UserAddress().setUserId("1").setAddress("杭州");
        return Arrays.asList(userAddress1, userAddress2);
    }
}

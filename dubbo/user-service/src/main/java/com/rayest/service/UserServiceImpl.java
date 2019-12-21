package com.rayest.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.rayest.model.User;
import org.springframework.stereotype.Component;

@Service
@Component
public class UserServiceImpl implements UserService {
    public User getByUserNo(String userId) {
        return new User().setId(-1).setUserNo("-1").setUsername("DEFAULT");
    }
}

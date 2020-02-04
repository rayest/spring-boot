package com.rayest.user.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.rayest.api.model.User;
import com.rayest.api.service.UserService;
import org.springframework.stereotype.Component;

@Service
@Component
public class UserServiceImpl implements UserService {
    public User getByUserNo(String userNo) {
        return new User().setId(-1).setUserNo(userNo).setUsername("DEFAULT");
    }
}

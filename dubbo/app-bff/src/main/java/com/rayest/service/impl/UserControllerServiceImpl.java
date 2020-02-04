package com.rayest.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.rayest.api.model.User;
import com.rayest.service.UserControllerService;
import com.rayest.api.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserControllerServiceImpl implements UserControllerService {

    @Reference(mock = "com.rayest.mock.UserServiceMock")
    private UserService userService;

    public User getByUserNo(String userNo) {
        return userService.getByUserNo(userNo);
    }
}

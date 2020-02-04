package com.rayest.mock;

import com.rayest.api.model.User;
import com.rayest.api.service.UserService;

public class UserServiceMock implements UserService {
    @Override
    public User getByUserNo(String userNo) {
        return new User().setId(-1).setUserNo("-1").setUsername("用户名-容错") ;
    }
}

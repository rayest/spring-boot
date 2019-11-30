package com.rayest.service;

import com.rayest.model.UserAddress;

import java.util.List;

public interface UserService {
    List<UserAddress> getByUserId(String userId);
}

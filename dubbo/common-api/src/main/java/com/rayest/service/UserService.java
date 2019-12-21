package com.rayest.service;

import com.rayest.model.User;
import com.rayest.model.UserAddress;

import java.util.List;

public interface UserService {
    User getByUserNo(String userNo);
}

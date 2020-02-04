package com.rayest.service;

import com.rayest.api.model.User;

public interface UserControllerService {
    User getByUserNo(String userNo);
}

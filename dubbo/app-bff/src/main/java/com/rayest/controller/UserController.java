package com.rayest.controller;

import com.rayest.api.model.User;
import com.rayest.service.UserControllerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class UserController {

    @Resource
    private UserControllerService userControllerService;

    @GetMapping("/user/{userNo}")
    public User getByUserNo(@PathVariable("userNo") String userNo){
        return userControllerService.getByUserNo(userNo);
    }
}

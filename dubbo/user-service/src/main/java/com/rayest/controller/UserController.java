package com.rayest.controller;

import com.rayest.model.UserAddress;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class UserController {

    @GetMapping("/user/address/{userId}")
    public UserAddress getByUserId(@PathVariable("userId") String userId){
        log.info("userId: {}", userId);
        return null;
    }
}

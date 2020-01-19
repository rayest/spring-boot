package com.rayest.controller;

import com.rayest.service.EmailFilterService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class EmailFilterController {

    @Resource
    private EmailFilterService emailFilterService;

    @PostMapping("/email")
    public void add(){
        emailFilterService.add();
    }

    @GetMapping("/email/{email}")
    public String getByEmail(@PathVariable String email){
        return emailFilterService.getByEmail(email);
    }
}

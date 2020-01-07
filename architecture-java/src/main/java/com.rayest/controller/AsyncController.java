package com.rayest.controller;

import com.rayest.service.AsyncService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class AsyncController {

    @Resource
    private AsyncService asyncService;

    @GetMapping("/async/future")
    public long queryAll(){
        return asyncService.queryAll();
    }

    @GetMapping("/async/completable/future")
    public long queryAll2(){
        return asyncService.queryAll2();
    }
}

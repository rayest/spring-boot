package com.rayest.rocketmq.producer;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class ProducerController {

    @Resource
    private ProducerService producerService;

    @PostMapping("/rocketmq/producer/user/{username}")
    public void send(@PathVariable String username){
        producerService.send(username);
    }
}

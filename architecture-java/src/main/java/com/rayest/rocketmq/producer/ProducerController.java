package com.rayest.rocketmq.producer;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class ProducerController {

    @Resource
    private ProducerService producerService;

    @PostMapping("/rocketmq/send/user/{username}")
    public void send(@PathVariable String username) {
        producerService.send(username);
    }

    @PostMapping("/rocketmq/convertAndSend/user/{username}")
    public void convertAndSend(@PathVariable String username) {
        producerService.convertAndSend(username);
    }

    @PostMapping("/rocketmq/asyncSend/user/{username}")
    public void asyncSend(@PathVariable String username) {
        producerService.asyncSend(username);
    }

    @PostMapping("/rocketmq/sendOneWay/user/{username}")
    public void sendOneWay(@PathVariable String username) {
        producerService.sendOneWay(username);
    }

    @PostMapping("/rocketmq/syncSendOrderly/user/{username}")
    public void syncSendOrderly(@PathVariable String username) {
        producerService.syncSendOrderly(username);
    }

    @PostMapping("/rocketmq/sendMessageInTransaction/user/{username}")
    public void sendMessageInTransaction(@PathVariable String username) throws Exception {
        producerService.sendMessageInTransaction(username);
    }



}

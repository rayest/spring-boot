package com.rayest.provider;


import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RabbitProvider {

    @Resource
    private RabbitTemplate rabbitTemplate;

    public void send(String message) {
        System.out.println("向消息队列发送消息");
        rabbitTemplate.convertAndSend("rabbit-queue", message);
    }
}

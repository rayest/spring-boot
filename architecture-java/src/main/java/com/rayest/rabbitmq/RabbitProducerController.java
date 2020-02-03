package com.rayest.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class RabbitProducerController {

    @Resource
    private RabbitTemplate rabbitTemplate;

    @PostMapping("/rabbit/send/{username}")
    public void send(@PathVariable String username){
        rabbitTemplate.convertAndSend("arch-queue", username);
    }
}

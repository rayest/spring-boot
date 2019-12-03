package com.rayest.springboot.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitConsumer {

    @RabbitListener(queues = "rabbit-queue")
    public void consumer(String msg){
        System.out.println("消费消息:" + msg);
    }
}

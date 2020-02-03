package com.rayest.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RabbitConsumer {

    @RabbitListener(queues = "arch-queue")
    public void consume(String username) {
        log.info("消费消息：username: {}", username);
    }
}

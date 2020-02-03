package com.rayest.rabbitmq;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class RabbitConsumer {

    @RabbitListener(queues = "arch-queue")
    public void consume(String username, Channel channel) {
        log.info("消费消息：username: {}", username);
        try {
            channel.basicAck(1l, false);
        } catch (IOException e) {
            log.warn("error: ", e);
        }
    }
}

package com.rayest.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitProducerConfig {
    public static final String QUEUE = "arch-queue";

    @Bean
    public Queue productQueue() {
        return new Queue(QUEUE, false);
    }
}

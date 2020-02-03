package com.rayest.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitProducerConfig {
    public static final String TEST_RABBIT_USER_QUEUE = "arch-queue";

    @Bean
    public Queue productQueue() {
        return new Queue(TEST_RABBIT_USER_QUEUE, false);
    }
}

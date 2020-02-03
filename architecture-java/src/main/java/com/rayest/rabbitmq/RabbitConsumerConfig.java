package com.rayest.rabbitmq;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitConsumerConfig {
    @Bean
    public RabbitConsumer consumer(){
        return new RabbitConsumer();
    }

}

package com.rayest.rabbitmq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@RestController
public class RabbitProducerController {

    @Resource
    private RabbitTemplate rabbitTemplate;

    @PostMapping("/rabbit/send/{username}")
    public void send(@PathVariable String username){
        CorrelationData correlationData = new CorrelationData();
        correlationData.setId(UUID.randomUUID().toString());
        rabbitTemplate.setConfirmCallback(confirmCallback());
        rabbitTemplate.setReturnCallback(returnCallback());
        rabbitTemplate.convertAndSend("", "arch-queue", username, correlationData);
    }

    private RabbitTemplate.ReturnCallback returnCallback() {
        return new RabbitTemplate.ReturnCallback(){
            @Override
            public void returnedMessage(Message message, int i, String s, String s1, String s2) {

            }
        };
    }

    private RabbitTemplate.ConfirmCallback confirmCallback(){
        return (correlationData, ack, result) -> {
            log.info("投递成功....");
            log.info("correlationData id: {}", correlationData.getId());
            log.info("ack: {}", ack);
            log.info("result: {}", result);
        };
    }
}

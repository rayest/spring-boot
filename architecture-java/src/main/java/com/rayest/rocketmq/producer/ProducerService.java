package com.rayest.rocketmq.producer;

import com.rayest.rocketmq.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class ProducerService {

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    public void send(String username) {
        User user = new User().setUsername(username);
        Message<User> message = new GenericMessage<>(user);
        rocketMQTemplate.send("arch-test-topic", message);
    }
}

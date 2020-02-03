package com.rayest.rocketmq.producer;

import com.rayest.common.Note;
import com.rayest.rocketmq.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Random;

@Slf4j
@Service
public class ProducerService {

    public static final String TOPIC = "arch-test-topic";
    public static final String TX_GROUP = "arch-test-tx-group";

    @Resource
    private RocketMQTemplate rocketMQTemplate;

    public void send(String username) {
        User user = new User().setUsername(username);
        Message<User> message = new GenericMessage<>(user);
        rocketMQTemplate.send(TOPIC, message);
    }

    @Note("同步发送")
    public void syncSend(String username) {
        User user = new User().setUsername(username);
        Message<User> message = new GenericMessage<>(user);
        SendResult sendResult = rocketMQTemplate.syncSend(TOPIC, message);
        log.info("sendResult: {}", sendResult);
    }

    public void convertAndSend(String username) {
        User user = new User().setUsername(username);
        rocketMQTemplate.convertAndSend(TOPIC, user);
    }

    @Note("单向发送，不关心发送结果")
    public void sendOneWay(String username) {
        User user = new User().setUsername(username);
        rocketMQTemplate.sendOneWay(TOPIC, user);
    }

    @Note("异步发送，需要定义回调方法，处理是否发送成功或者异常")
    public void asyncSend(String username) {
        User user = new User().setUsername(username);
        rocketMQTemplate.asyncSend(TOPIC, user, sendCallback());
    }

    @Note("以事务的形式发送")
    public void sendMessageInTransaction(String username) throws Exception {
        User user = new User().setUsername(username);
        Message<User> message = new GenericMessage<>(user);
        TransactionSendResult sendResult = rocketMQTemplate.sendMessageInTransaction(TX_GROUP, TOPIC, message, "1");
        log.info("事务消息：sendResult: {}", sendResult);
        if (LocalTransactionState.ROLLBACK_MESSAGE.equals(sendResult.getLocalTransactionState())){
            log.info("回滚...");
            throw new Exception("请重试");
        }
    }

    @Note("顺序发送。自定义算法，发送到指定的队列上")
    public void syncSendOrderly(String username) {
        User user = new User().setUsername(username);
        SendResult result = rocketMQTemplate.syncSendOrderly(TOPIC, user, hashKey());
        log.info("result: {}", result);
    }

    private String hashKey() {
        Random random = new Random();
        int i = random.nextInt();
        String key = String.valueOf(i % 4);
        log.info("hashKey: {}", key);
        return key;
    }

    private SendCallback sendCallback() {
        return new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                log.info("异步发送成功：result: {}", sendResult);
            }

            @Override
            public void onException(Throwable throwable) {
                log.warn("异步发送失败：result", throwable);
            }
        };
    }

    public void sendWihTag(String username) {
        User user = new User().setUsername(username);
        String tag = "test-tag";
        rocketMQTemplate.asyncSend(TOPIC + ":" + tag , user, sendCallback());
    }
}

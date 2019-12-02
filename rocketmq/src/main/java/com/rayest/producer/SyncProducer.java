package com.rayest.producer;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

// 同步发送消息
public class SyncProducer {
    public static void send() throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("group1");
        producer.setNamesrvAddr("127.0.0.1:9876");
        producer.start();
        for (int i = 0; i < 10; i++) {
            Message message = new Message("rocket-topic1", "tag1", ("hello:" + i).getBytes());
            SendResult result = producer.send(message);
            System.out.println("result:" + result);
            Thread.sleep(1000);
        }

        producer.shutdown();
    }
}

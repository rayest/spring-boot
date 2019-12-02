package com.rayest.producer;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;

// 异步发送消息
public class AsyncProducer {
    public static void send() throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("group1");
        producer.setNamesrvAddr("127.0.0.1:9876");
        producer.start();
        for (int i = 0; i < 10; i++) {
            Message message = new Message("rocket-topic-async", "tag2", ("hello:" + i).getBytes());
            producer.send(message, new SendCallback() {
                public void onSuccess(SendResult sendResult) {
                    System.out.println("发送成功: " + sendResult);
                }
                public void onException(Throwable e) {
                    System.out.println("发送失败");
                }
            });
            Thread.sleep(1000);
        }

        producer.shutdown();
    }
}

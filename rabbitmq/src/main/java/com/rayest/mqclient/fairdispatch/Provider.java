package com.rayest.mqclient.fairdispatch;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rayest.mqclient.ConnectionUtils;

public class Provider {
    private static final String QUEUE = "work-queues";

    public static void send() throws Exception {
        Connection connection = ConnectionUtils.getConnection(); // 创建连接
        Channel channel = connection.createChannel(); // 创建信道
        channel.queueDeclare(QUEUE, false, false, false, null); // 声明队列
        channel.basicQos(1); // 消费者向生产者发送确认消息前，队列中的消息不被消费。限制一条消息

        for (int i = 0; i < 20; i++) {
            String message = "Hello work queue: " + i;
            channel.basicPublish("", QUEUE, null, message.getBytes());
            Thread.sleep(20);
            System.out.println("发送消息：" + i);
        }
    }
}

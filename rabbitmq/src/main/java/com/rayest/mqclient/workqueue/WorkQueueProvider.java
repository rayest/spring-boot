package com.rayest.mqclient.workqueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rayest.mqclient.ConnectionUtils;

public class WorkQueueProvider {
    private static final String QUEUE = "work-queues";

    public static void send() throws Exception {
        Connection connection = ConnectionUtils.getConnection(); // 创建连接
        Channel channel = connection.createChannel(); // 创建信道
        channel.queueDeclare(QUEUE, false, false, false, null); // 声明队列

        for (int i = 0; i < 20; i++) {
            String message = "Hello work queue: " + i;
            channel.basicPublish("", QUEUE, null, message.getBytes());
            Thread.sleep(20);
        }
        System.out.println("发送消息成功");
        channel.close();
        connection.close();
    }
}

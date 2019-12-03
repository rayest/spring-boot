package com.rayest.mqclient.simplequeue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rayest.mqclient.ConnectionUtils;

public class ClientProvider {
    private static final String QUEUE = "client-queue";

    public static void send() throws Exception {
        Connection connection = ConnectionUtils.getConnection(); // 创建连接
        Channel channel = connection.createChannel(); // 创建信道
        channel.queueDeclare(QUEUE, false, false, false, null); // 声明队列
        String message = "Hello";
        channel.basicPublish("", QUEUE, null, message.getBytes());
        System.out.println("发送消息成功");// 发送消息
        channel.close();
        connection.close();
    }
}

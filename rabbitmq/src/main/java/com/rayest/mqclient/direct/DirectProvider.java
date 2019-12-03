package com.rayest.mqclient.direct;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rayest.mqclient.ConnectionUtils;

public class DirectProvider {

    public static final String EXCHANGE = "direct-exchange";
    public static final String ROUTING_KEY_ERROR = "info";

    public static void send() throws Exception {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel(); // 创建信道
        channel.exchangeDeclare(EXCHANGE, "direct"); // 声明交换机
        String message = "hello direct";
        channel.basicPublish(EXCHANGE, ROUTING_KEY_ERROR, null, message.getBytes());
        System.out.println("消息发送成功");
        channel.close();
        connection.close();
    }
}

package com.rayest.mqclient.confirm;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rayest.mqclient.ConnectionUtils;

public class ConfirmProvider {
    public static final String CONFIRM_QUEUE = "confirm-queue";

    public static void send() throws Exception {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel(); // 创建信道
        channel.queueDeclare(CONFIRM_QUEUE, false, false, false, null);
        String message = "hello confirm";
        channel.confirmSelect();// confirm 确认机制
        channel.basicPublish("", CONFIRM_QUEUE, null, message.getBytes());
        if (channel.waitForConfirms()) {
            System.out.println("消息发送成功");
        } else {
            System.out.println("消息发送失败");
        }

        channel.close();
        connection.close();
    }
}

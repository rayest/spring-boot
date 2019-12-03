package com.rayest.mqclient.confirm;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rayest.mqclient.ConnectionUtils;

import java.io.IOException;

public class ConfirmConsumer {
    public static final String CONFIRM_QUEUE = "confirm-queue";

    public static void send() throws Exception {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel(); // 创建信道
        channel.queueDeclare(CONFIRM_QUEUE, false, false, false, null);
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消息消费成功: " + new String(body));
            }
        };

        channel.basicConsume(CONFIRM_QUEUE, consumer);
    }
}

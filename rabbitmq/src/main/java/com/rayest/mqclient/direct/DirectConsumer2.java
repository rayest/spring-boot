package com.rayest.mqclient.direct;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rayest.mqclient.ConnectionUtils;

import java.io.IOException;

public class DirectConsumer2 {

    public static final String QUEUE = "direct-queue2";
    public static final String EXCHANGE = "direct-exchange";
    public static final String ROUTING_KEY_ERROR = "error";
    public static final String ROUTING_KEY_INFO = "info";
    public static final String ROUTING_KEY_WARN = "warning";

    public static void consumer() throws Exception {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel(); // 创建信道

        channel.queueDeclare(QUEUE, false, false, false, null); // 声明队列
        channel.queueBind(QUEUE, EXCHANGE, ROUTING_KEY_ERROR);
        channel.queueBind(QUEUE, EXCHANGE, ROUTING_KEY_INFO);
        channel.queueBind(QUEUE, EXCHANGE, ROUTING_KEY_WARN);
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费消息:" + new String(body));
            }
        };
        channel.basicConsume(QUEUE, consumer);
    }
}

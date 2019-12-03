package com.rayest.mqclient.workqueue;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BasicProperties;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rayest.mqclient.ConnectionUtils;

import java.io.IOException;

public class WorkQueueConsumer1 {
    private static final String QUEUE = "work-queues";

    public static void consumer() throws Exception {
        Connection connection = ConnectionUtils.getConnection(); // 创建连接
        Channel channel = connection.createChannel(); // 创建信道
        channel.queueDeclare(QUEUE, false, false, false, null); // 声明队列
        Consumer consumer = new DefaultConsumer(channel) {
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("[1]消费消息：" + new String(body));
                try {
                    Thread.sleep(1000);
                } catch (Exception e) {

                } finally {
                    System.out.println("[1]消费结束");
                }
            }
        };

        boolean autoAck = true;
        channel.basicConsume(QUEUE, autoAck, consumer);

    }
}

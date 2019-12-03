package com.rayest.mqclient.fairdispatch;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import com.rayest.mqclient.ConnectionUtils;

import java.io.IOException;

public class Consumer2 {
    private static final String QUEUE = "work-queues";

    public static void consumer() throws Exception {
        Connection connection = ConnectionUtils.getConnection(); // 创建连接
        Channel channel = connection.createChannel(); // 创建信道
        channel.queueDeclare(QUEUE, false, false, false, null); // 声明队列
        channel.basicQos(1);
        Consumer consumer = new DefaultConsumer(channel) {
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("[2]消费消息：" + new String(body));
                try {
                    Thread.sleep(2000);
                } catch (Exception e) {

                } finally {
                    System.out.println("[2]消费结束");
                    channel.basicAck(envelope.getDeliveryTag(), false); // 手动应答

                }
            }
        };

        boolean autoAck = false;
        channel.basicConsume(QUEUE, autoAck, consumer);
    }
}

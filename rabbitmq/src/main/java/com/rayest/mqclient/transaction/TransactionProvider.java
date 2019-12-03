package com.rayest.mqclient.transaction;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rayest.mqclient.ConnectionUtils;

public class TransactionProvider {
    public static final String TX_QUEUE = "tx_queue";

    public static void send() throws Exception {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(TX_QUEUE, false, false, false, null);
        String message = "hello_tx";

        try {
            channel.txSelect();
            channel.basicPublish("", TX_QUEUE, null, message.getBytes());
            System.out.println("消息发送成功");
            channel.txCommit();
        } catch (Exception e) {
            channel.txRollback();
            System.out.println("消息发送失败,回滚");
        }

        channel.close();
        connection.close();
    }
}

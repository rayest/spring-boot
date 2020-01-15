package com.rayest.transaction;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;

public class TransactionProducer {
    public static void main(String[] args) throws Exception {
        sendTransaction();
    }
    public static void sendTransaction() throws Exception {
        TransactionMQProducer producer = new TransactionMQProducer("trans-group");
        producer.setNamesrvAddr("127.0.0.1:9876");

        // 设置事务监听器
        producer.setTransactionListener(transactionListener());

        producer.start();

        for (int i = 0; i < 10; i++) {
            Message message = new Message("transaction-topic", "tag3", ("Hello:" + i).getBytes());
            TransactionSendResult result = producer.sendMessageInTransaction(message, null);
            System.out.println("发送结果:" + result);
            Thread.sleep(1000);
        }

    }

    public static TransactionListener transactionListener() {
        return new TransactionListener() {

            // 执行 MQ 的本地事务
            public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
                System.out.println("msg: " + msg);
                System.out.println("arg: " + arg);
                if (msg.getTags().equals("tag1")) {
                    return LocalTransactionState.COMMIT_MESSAGE;
                }
                if (msg.getTags().equals("tag2")) {
                    return LocalTransactionState.ROLLBACK_MESSAGE;
                }
                if (msg.getTags().equals("tag3")) {
                    return LocalTransactionState.UNKNOW;
                }
                return LocalTransactionState.UNKNOW;
            }

            // MQ 回查本地事务状态
            public LocalTransactionState checkLocalTransaction(MessageExt msg) {
                System.out.println("消息tag：" + msg.getTags());
                return LocalTransactionState.COMMIT_MESSAGE;
            }
        };
    }
}

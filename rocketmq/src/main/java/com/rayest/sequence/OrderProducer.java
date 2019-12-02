package com.rayest.sequence;

import com.rayest.model.Order;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;

import java.util.List;

public class OrderProducer {
    public static void send() throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("group1");
        producer.setNamesrvAddr("127.0.0.1:9876");
        producer.start();
        List<Order> orderList = Order.buildOrders();
        for (int i = 0; i < orderList.size(); i++) {
            String body = orderList.get(i) + "";
            Message message = new Message("order-topic", "order", "i" + i, body.getBytes());
            SendResult sendResult = producer.send(message, new MessageQueueSelector() {
                public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                    // 定义队列选择规则
                    Long orderId = (Long) arg; // arg 即为业务定义的对象
                    long index = orderId % (mqs.size()); // 订单号 % 队列大小。作为队列下标
                    return mqs.get((int) index); // 获取队列
                }
            }, orderList.get(i).getOrderId()); // 即 arg
            System.out.println("消息发送结果。result: "  + sendResult);
        }
        producer.shutdown();
    }
}

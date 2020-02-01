package com.rayest.disruptor;

import com.lmax.disruptor.RingBuffer;
import com.rayest.common.Note;

import java.util.concurrent.atomic.AtomicInteger;

@Note("定义生产者，也就是事件的来源")
public class Producer implements Runnable {
    public static final int NUMBER = 100;
    private RingBuffer<Product> ringBuffer;
    private AtomicInteger idCount = new AtomicInteger(0);

    public Producer(RingBuffer<Product> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    @Override
    public void run() {
        for (int i = 0; i < NUMBER; i++) {
            produce();
        }
    }

    @Note("Disruptor 的事件发布过程是一个两阶段提交的过程")
    private void produce() {
        // 1. 可以把ringBuffer看做一个事件队列，那么next就是得到下面一个事件槽
        long sequence = ringBuffer.next();
        try {
            // 2. 用上面的索引取出一个空的事件用于填充（获取该序号对应的事件对象）
            Product product = ringBuffer.get(sequence);

            // 3. 获取要通过事件传递的业务数据
            product.setId(idCount.incrementAndGet());
            System.out.println("开始生产消息---product: " + product.toString());
        } finally {
            // 4. 发布事件
            // 注意，最后的 ringBuffer.publish 方法必须包含在 finally 中以确保必须得到调用；
            // 如果某个请求的 sequence 未被提交，将会堵塞后续的发布操作或者其它的 producer。
            ringBuffer.publish(sequence);
        }
    }
}

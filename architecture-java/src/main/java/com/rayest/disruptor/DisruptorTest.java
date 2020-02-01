package com.rayest.disruptor;

import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.YieldingWaitStrategy;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DisruptorTest {
    // 定义ringBuffer的大小
    private static final int RING_BUFFER_SIZE = 1024 * 8;

    public static void main(String[] args) {

        Disruptor<Product> disruptor = startDisruptor();

        produce(disruptor);
    }

    private static void produce(Disruptor<Product> disruptor) {
        Producer producer = new Producer(disruptor.getRingBuffer());
        ExecutorService producerExecutor = Executors.newSingleThreadExecutor();
        producerExecutor.submit(producer);
    }

    private static Disruptor<Product> startDisruptor() {
        Disruptor<Product> disruptor = new Disruptor<>(eventFactory(), RING_BUFFER_SIZE, DaemonThreadFactory.INSTANCE, ProducerType.SINGLE, new YieldingWaitStrategy());
        Consumer consumer = new Consumer();
        disruptor.handleEventsWith(consumer);
        disruptor.start();
        return disruptor;
    }

    private static EventFactory<Product> eventFactory() {
        return Product::new;
    }
}

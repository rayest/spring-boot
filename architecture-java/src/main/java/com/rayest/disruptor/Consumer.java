package com.rayest.disruptor;

import com.lmax.disruptor.EventHandler;

public class Consumer implements EventHandler<Product> {

    public Consumer() {
    }

    @Override
    public void onEvent(Product product, long sequence, boolean endOfBatch) throws Exception {
        System.out.println("开始消费消息...product: " + product.toString());
    }
}

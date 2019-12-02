package com.rayest;

import com.rayest.consumer.Consumer;
import com.rayest.sequence.OrderConsumer;
import com.rayest.sequence.OrderProducer;

public class Application {
    public static void main(String[] args) throws Exception {
        OrderConsumer.consumer();
    }
}

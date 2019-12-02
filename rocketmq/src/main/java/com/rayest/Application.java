package com.rayest;

import com.rayest.producer.OnewayProducer;

public class Application {
    public static void main(String[] args) throws Exception {
        OnewayProducer.send();
    }
}

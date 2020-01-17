package com.rayest.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
public class AtomicIntegerService {
    private final AtomicInteger atomicInteger = new AtomicInteger(0);

    public void getById(String bookId) {
        if (atomicInteger.get() >= 10) {
            log.info("服务正忙，请稍后再试");
            return;
        }
        atomicInteger.incrementAndGet();
        log.info("处理业务,bookId: {}", bookId);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            log.info("e:{}", e.getMessage());
        } finally {
            atomicInteger.decrementAndGet();
        }
    }
}

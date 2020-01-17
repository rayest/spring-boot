package com.rayest.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.Semaphore;

@Slf4j
@Service
public class SemaphoreService {
    // 允许 10 个并发
    private final Semaphore permit = new Semaphore(10, true);

    public void getById(String filmId) {
        if (permit.getQueueLength() > 100) {
            log.info("当前服务器忙，请稍后再试");
            return;
        }
        try {
            permit.acquire();
            log.info("获得许可，剩余可用许可：{}", permit.availablePermits());

            log.info("filmId: {}", filmId);
            log.info("处理业务中...");
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            log.info("异常：{}", e.getMessage());
        } finally {
            permit.release();
            log.info("释放许可，剩余可用许可：{}", permit.availablePermits());
        }
    }
}

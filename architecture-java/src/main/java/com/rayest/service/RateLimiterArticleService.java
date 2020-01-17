package com.rayest.service;

import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class RateLimiterArticleService {

    private RateLimiter rateLimiter = RateLimiter.create(10);

    public void getById(String articleId) {
        rateLimiter.acquire(1);
        log.info("处理业务. articleId: {}", articleId);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            log.info("e: {}", e.getMessage());
        }
    }
}

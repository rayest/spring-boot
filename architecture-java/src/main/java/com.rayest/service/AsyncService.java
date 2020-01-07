package com.rayest.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
@Service
public class AsyncService {

    @Resource
    private ThreadPoolExecutor executor;

    public long queryAll() {
        long start = System.currentTimeMillis();
        Future<Long> bookFuture = executor.submit(this::queryBooks);
        Future<Long> articleFuture = executor.submit(this::queryArticles);
        Future<Long> filmFuture = executor.submit(this::queryFilms);
        return getResult(start, bookFuture, articleFuture, filmFuture);
    }

    private long getResult(long start, Future<Long> bookFuture, Future<Long> articleFuture, Future<Long> filmFuture) {
        long result = 0;
        try {
            result = bookFuture.get() + articleFuture.get() + filmFuture.get();
        } catch (Exception e) {
            log.info("error: {}", e.getMessage());
        }
        long end = System.currentTimeMillis();
        log.info("elapsedTime: {}", end - start);
        return result;
    }

    private long queryBooks() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            log.info("错误: {}", e.getMessage());
        }
        return 1;
    }

    private long queryFilms() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            log.info("错误: {}", e.getMessage());
        }
        return 2;
    }

    private long queryArticles() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            log.info("错误: {}", e.getMessage());
        }
        return 3;
    }

    public long queryAll2() {
        long start = System.currentTimeMillis();
        CompletableFuture<Long> articleFuture = CompletableFuture.supplyAsync(this::queryArticles);
        CompletableFuture<Long> bookFuture = CompletableFuture.supplyAsync(this::queryBooks);
        CompletableFuture<Long> filmFuture = CompletableFuture.supplyAsync(this::queryFilms);
        return getResult(start, articleFuture, bookFuture, filmFuture);
    }
}

package com.rayest.common;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class GuavaCacheConfig {

    @Bean
    public Cache<String, String> cacheBuilder(){
        return CacheBuilder
                .newBuilder()
                .concurrencyLevel(4)
                .expireAfterWrite(10, TimeUnit.SECONDS) // 10 秒后过期
                .maximumSize(10000) // 最大缓存数
                .build();
    }
}

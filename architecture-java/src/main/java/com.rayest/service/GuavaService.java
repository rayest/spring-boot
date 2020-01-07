package com.rayest.service;

import com.google.common.cache.Cache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class GuavaService {

    @Resource
    private Cache<String, String> cache;



    public String getById(String filmId) {
        String filmName = cache.getIfPresent(filmId);
        log.info("filmName: {}", filmName);
        if (filmName == null) {
            log.info("本地缓存不存在记录，请求数据库...");
            filmName = "Last Wind";
            cache.put(filmId, filmName);
        }
        return filmName;
    }
}

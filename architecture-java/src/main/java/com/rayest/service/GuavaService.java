package com.rayest.service;

import com.google.common.cache.Cache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
@Service
public class GuavaService {

    @Resource
    private Cache<String, String> cache;

    @Resource
    private ThreadPoolExecutor executor;

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

    public void update(String filmId) {
        String filmName = "Gone with wind";
        cache.put(filmId, filmName);
        executor.execute(() -> updateFilm(filmName));
    }

    private void updateFilm(String filmName) {
        log.info("异步更新数据库. filmName: [{}]", filmName);
    }
}

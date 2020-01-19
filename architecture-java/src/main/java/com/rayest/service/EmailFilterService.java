package com.rayest.service;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import com.rayest.common.Note;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.util.UUID;

@Service
@Slf4j
public class EmailFilterService {

    private static int size = 1_000_000;
    private static BloomFilter<String> bloomFilter = BloomFilter.create(Funnels.stringFunnel(Charset.defaultCharset()), size);

    @Note("新增数据到数据库和缓存时，为避免缓存穿透，将必要的数据添加到 bloom 过滤器中")
    public void add() {
        String uuid = UUID.randomUUID().toString();
        log.info("uuid: {}", uuid);
        // 1. add to db
        // 2. add to redis cache

        // 3. add to bloom filter
        bloomFilter.put(uuid + "@test.com");
    }

    public String getByEmail(String email) {
        // 1. 先通过 bloom filter 过滤不存在的数据的请求
        if (!bloomFilter.mightContain(email)) {
            return "不存在的请求";
        }
        return "数据存在，从缓存或者数据库中获取";
    }
}

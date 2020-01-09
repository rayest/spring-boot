package com.rayest;

import com.rayest.common.Note;
import org.junit.Test;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ZSetOperations;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

public class RedisTest extends BaseTest {

    @Resource
    private RedisTemplate<String, String> stringRedisTemplate;

    @Test
    public void test_string() {
        String key = "name-string";
        String value = "lee";
        stringRedisTemplate.opsForValue().set(key, value);
        stringRedisTemplate.expire(key, 60, TimeUnit.SECONDS);

        String name = stringRedisTemplate.opsForValue().get(key);

        assertEquals("lee", name);
    }

    @Test
    @Note("列表. 左进右出/右进左出")
    public void test_right_list() {
        String key = "colors";
        List<String> colors = Arrays.asList("RED", "BLACK", "BLUE");
        stringRedisTemplate.opsForList().rightPushAll(key, colors);
        stringRedisTemplate.expire(key, 60, TimeUnit.SECONDS);
        String red = stringRedisTemplate.opsForList().leftPop(key);

        assertEquals("RED", red);
    }

    @Test
    public void test_set() {
        String key = "users";
        stringRedisTemplate.opsForSet().add(key, "1", "2", "3");
        SetOperations<String, String> set = stringRedisTemplate.opsForSet();

        Long size = set.size(key);
        assert size != null;

        assertEquals(3, size.intValue());
        assertEquals(true, set.isMember(key, "1"));
    }

    @Test
    public void test_hash() {
        String key = "user-lee";
        Map<String, String> users = new HashMap<>();
        users.put("name", "lee");
        users.put("age", "20");
        users.put("city", "shanghai");
        stringRedisTemplate.opsForHash().putAll(key, users);

        Map<Object, Object> map = stringRedisTemplate.opsForHash().entries(key);
        assertEquals("lee", map.get("name"));
    }

    @Test
    @Note("缓存三个用户的粉丝数量")
    public void test_z_set() {
        String key = "fans";
        stringRedisTemplate.opsForZSet().add(key, "Lee", 1000);
        stringRedisTemplate.opsForZSet().add(key, "Adel", 2000);
        stringRedisTemplate.opsForZSet().add(key, "Zoe", 3000);

        ZSetOperations<String, String> zSet = stringRedisTemplate.opsForZSet();
        assertEquals(3, zSet.size(key).intValue());
        assertEquals(1000, zSet.score(key, "Lee").intValue());
        assertEquals(0, zSet.rank(key, "Lee").intValue());
        assertEquals(1, zSet.rank(key, "Adel").intValue());
        assertEquals(2, zSet.rank(key, "Zoe").intValue());
    }

    @Test
    @Note("scan(): 过滤集合中的元素")

    public void test_scan() {
        String key = "scan-key";
        stringRedisTemplate.opsForSet().add(key, "key1001", "key1002", "key2000");
        ScanOptions scanOptions = ScanOptions.scanOptions().match("key1*").count(3).build();
        Cursor<String> cursor = stringRedisTemplate.opsForSet().scan(key, scanOptions);
        int count = 0;
        while (cursor.hasNext()){
            String value = cursor.next();
            System.out.println(value);
            count++;
        }
        assertEquals(2, count);
    }
}

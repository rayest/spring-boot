package com.rayest;

import com.rayest.common.Note;
import org.junit.Test;
import org.springframework.data.redis.core.RedisTemplate;
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
    private RedisTemplate<String, String> stringStringRedisTemplate;

    @Test
    public void test_string() {
        String key = "name-string";
        String value = "lee";
        stringStringRedisTemplate.opsForValue().set(key, value);
        stringStringRedisTemplate.expire(key, 60, TimeUnit.SECONDS);

        String name = stringStringRedisTemplate.opsForValue().get(key);

        assertEquals("lee", name);
    }

    @Test
    @Note("列表. 左进右出/右进左出")
    public void test_right_list() {
        String key = "colors";
        List<String> colors = Arrays.asList("RED", "BLACK", "BLUE");
        stringStringRedisTemplate.opsForList().rightPushAll(key, colors);
        stringStringRedisTemplate.expire(key, 60, TimeUnit.SECONDS);
        String red = stringStringRedisTemplate.opsForList().leftPop(key);

        assertEquals("RED", red);
    }

    @Test
    public void test_set() {
        String key = "users";
        stringStringRedisTemplate.opsForSet().add(key, "1", "2", "3");
        SetOperations<String, String> set = stringStringRedisTemplate.opsForSet();

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
        stringStringRedisTemplate.opsForHash().putAll(key, users);

        Map<Object, Object> map = stringStringRedisTemplate.opsForHash().entries(key);
        assertEquals("lee", map.get("name"));
    }

    @Test
    @Note("缓存三个用户的粉丝数量")
    public void test_z_set() {
        String key = "fans";
        stringStringRedisTemplate.opsForZSet().add(key, "Lee", 1000);
        stringStringRedisTemplate.opsForZSet().add(key, "Adel", 2000);
        stringStringRedisTemplate.opsForZSet().add(key, "Zoe", 3000);

        ZSetOperations<String, String> zSet = stringStringRedisTemplate.opsForZSet();
        assertEquals(3, zSet.size(key).intValue());
        assertEquals(1000, zSet.score(key, "Lee").intValue());
        assertEquals(0, zSet.rank(key, "Lee").intValue());
        assertEquals(1, zSet.rank(key, "Adel").intValue());
        assertEquals(2, zSet.rank(key, "Zoe").intValue());
    }
}

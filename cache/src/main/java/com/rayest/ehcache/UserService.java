package com.rayest.ehcache;

import com.rayest.common.Note;
import com.rayest.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {

    @Note("查询。默认情况下，缓存的 key 就是方法的参数，缓存的 value 就是方法的返回值")
    @Cacheable(key = "#id", cacheNames = "user")
    public User getUserById(Integer id) {
        log.info("查询用户。id: {}", id);
        return new User().setId(id).setUsername("lee");
    }

    @Note("更新。当数据库中的数据更新后，缓存中的数据也要跟着更新")
    @CachePut(key = "#user.id", cacheNames = "user")
    public User updateUserById(User user) {
        log.info("更新用户。user: {}", user);
        return new User().setId(1).setUsername(user.getUsername());
    }

    @Note("删除。当数据库中的数据删除后，相关的缓存数据也要自动清除")
    @CacheEvict(cacheNames = "user")
    public void deleteUserById(Integer id) {
        log.info("删除。id:{}", id);
    }

}

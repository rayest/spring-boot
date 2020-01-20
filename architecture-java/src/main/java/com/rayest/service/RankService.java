package com.rayest.service;

import com.rayest.common.Note;
import com.rayest.model.UsersRank;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class RankService {
    public static final String USER_RANK_WEEKLY =  "user-rank-weekly";

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    public void updateByWeekly() {
        // 1. update and add to mysql
        // 2. add to redis by zset
        redisTemplate.opsForZSet().add(USER_RANK_WEEKLY, init());
    }

    private Set<ZSetOperations.TypedTuple<String>> init() {
        Set<ZSetOperations.TypedTuple<String>> set = new HashSet<>();
        set.add(new DefaultTypedTuple<>("Adel", 1.0));
        set.add(new DefaultTypedTuple<>("Cindy", 3.0));
        set.add(new DefaultTypedTuple<>("Black", 2.0));
        set.add(new DefaultTypedTuple<>("Eva", 5.0));
        set.add(new DefaultTypedTuple<>("David", 4.0));
        return set;
    }

    public List<UsersRank> getWeekly() {
        List<UsersRank> usersRankList = new ArrayList<>();
        ZSetOperations<String, String> zSetOperations = redisTemplate.opsForZSet();
        Set<ZSetOperations.TypedTuple<String>> users = zSetOperations.rangeWithScores(USER_RANK_WEEKLY, 0, -1);
        for (ZSetOperations.TypedTuple<String> user : users) {
            Long rank = zSetOperations.rank(USER_RANK_WEEKLY, user.getValue()) + 1;
            usersRankList.add(new UsersRank().setUsername(user.getValue()).setScore(user.getScore()).setRank(rank));
        }
        return usersRankList;
    }

    @Note("类似于微博实时排行榜")
    public void addOrUpdateRank(String username) {
        // 1. 用户浏览时更新数据库
        // 2. 更新 Redis
        ZSetOperations<String, String> zSetOperations = redisTemplate.opsForZSet();

        // 没有则新增，有则更新得分
        zSetOperations.incrementScore(USER_RANK_WEEKLY, username, 1.0);
    }
}

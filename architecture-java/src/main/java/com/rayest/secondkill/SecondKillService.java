package com.rayest.secondkill;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.UUID;

@Slf4j
@Service
public class SecondKillService {
    public static final String KILL_GOOD = "sec:kill:good:";

    @Resource
    private GoodStockMapper goodStockMapper;

    // 假设最大100万请求，允许同时最大5000请求，其余过滤掉
    public static final int LIMIT = 5000;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public void getGoodDetail(String goodNo) {
        // todo 从 redis 中获取秒杀商品详情
    }

    public void add() {
        // 1. 通过后台管理系统添加秒杀商品
        // 2. 将需要显示的详情信息和库存缓存到 redis 中
    }

    public void kill(String goodNo, String userNo) throws Exception {
        // 0. 判断用户 userNo 是否已经参与抢购
        // 1. 通过 lua 脚本单线程原子性实现限流。判断和更新允许最大请求数，过滤和忽略其余的用户请求
        // 2. 查库存
        String key = KILL_GOOD + "lock:" + goodNo + ":" +userNo;
        String value = UUID.randomUUID().toString();
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        Boolean lock = ops.setIfAbsent(key, value);
        if (lock) {
            try {
                if (getStock(goodNo) <= 0) {
                    throw new Exception("已抢完");
                }
                // 3. 更新库存或者通过 MQ 扣减等其他可异步的操作
                int updated = deductStock(goodNo);
                if (updated == 0) {
                    throw new Exception("已抢完");
                }
            } catch (Exception e) {
                log.info("异常 e", e);
            } finally {
                if (value.equals(ops.get(key))) {
                    stringRedisTemplate.delete(key);
                }
            }
        }
    }

    private int deductStock(String goodNo) {
        return goodStockMapper.deductStock(goodNo);
    }

    private long getStock(String goodNo) {
        return goodStockMapper.getStock(goodNo);
    }
}

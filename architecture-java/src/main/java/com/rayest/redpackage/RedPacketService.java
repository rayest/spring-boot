package com.rayest.redpackage;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class RedPacketService {

    public static final String RED_PACKAGE_KEY = "rayson:red:package:";
    public static final String RED_PACKAGE_LIST_KEY = "rayson:red:package:list:";

    @Resource
    private RedPacketMapper redPacketMapper;

    @Resource
    private RedPacketDetailMapper redPacketDetailMapper;

    @Resource
    private RedisTemplate<String, Object> stringRedisTemplate;

    @Transactional(rollbackFor = Exception.class)
    public void save(RedPackageDTO redPackageDTO) {
        // 1. 红包 summary 存入数据库
        String redPackageNo = UUID.randomUUID().toString();
        RedPackageEntity redPackageEntity = new RedPackageEntity()
                .setRedPackageNo(redPackageNo)
                .setTotalAmount(Integer.valueOf(redPackageDTO.getTotalAmount()))
                .setTotalNumber(Integer.parseInt(redPackageDTO.getTotalNumber()))
                .setUserNo(redPackageDTO.getUserNo());
        redPacketMapper.save(redPackageEntity);

        // 4. 缓存红包总金额
        stringRedisTemplate.opsForValue().set(RED_PACKAGE_KEY + redPackageNo, redPackageDTO.getTotalNumber(), 24, TimeUnit.HOURS);

        // 5. 以 LIST 数据结构存入 redis
        stringRedisTemplate.opsForList().leftPushAll(RED_PACKAGE_LIST_KEY + redPackageNo, "100", "200", "300");
    }

    private void saveRedPackageDetail(String redPackageNo, String userNo, Integer amount) {
        RedPackageDetailEntity redPackageDetailEntity = new RedPackageDetailEntity()
                .setRedPackageNo(redPackageNo)
                .setUserNo(userNo)
                .setAmount(amount);
        redPacketDetailMapper.save(redPackageDetailEntity);
    }

    // 2. 随机分配红包金额
    private List<Integer> divideRedPackage(String totalAmount, String totalNumbers) {
        // 具体金额算法忽略，目前写死。单位为 分
        return Arrays.asList(100, 200, 300);
    }

    public RedPackageVO get(String userNo, String redPackageNo) throws Exception {
        RedPackageDetailEntity redPackageDetailEntity = redPacketDetailMapper.selectByUserNo(userNo, redPackageNo);
        if (null != redPackageDetailEntity){
            throw new Exception("您已抢过红包，不能重复抢红包");
        }

        String value = (String) stringRedisTemplate.opsForValue().get(RED_PACKAGE_KEY + redPackageNo);
        log.info("value: {}", value);
        if (StringUtils.isEmpty(value)){
            throw new Exception("红包已抢完");
        }
        int totalNumber = Integer.parseInt(value);
        if (totalNumber <= 0){
            throw new Exception("红包已抢完");
        }
        // 2. 随机选出红包金额
        String amount = (String) stringRedisTemplate.opsForList().rightPop(RED_PACKAGE_LIST_KEY + redPackageNo);

        // 3. 红包 detail 存入数据库
        saveRedPackageDetail(redPackageNo, userNo, Integer.parseInt(amount));

        // 4. 更新缓存
        totalNumber = totalNumber - 1;
        if (totalNumber <= 0){
            stringRedisTemplate.opsForValue().getOperations().delete(RED_PACKAGE_KEY + redPackageNo);
        } else {
            stringRedisTemplate.opsForValue().set(RED_PACKAGE_KEY + redPackageNo, String.valueOf(totalNumber));
        }

        // 5. 根据需要显示前端必要的数据信息
        return new RedPackageVO().setUserNo(userNo).setAmount(Integer.parseInt(amount)).setCreatedTime(LocalDateTime.now());
    }
}

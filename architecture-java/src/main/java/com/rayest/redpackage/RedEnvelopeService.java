package com.rayest.redpackage;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class RedEnvelopeService {

    public static final String RED_PACKAGE_KEY = "rayson:red:envelope:";
    public static final String RED_PACKAGE_LIST_KEY = "rayson:red:envelope:list:";

    private static final String PREFIX = "red_envelope_list_";

    @Resource
    private RedEnvelopeMapper redEnvelopeMapper;

    @Resource
    private RedEnvelopeRecordMapper redEnvelopeRecordMapper;

    @Resource
    private RedisTemplate<String, Object> stringRedisTemplate;

    @Resource
    private DefaultRedisScript<String> redisScript;

    @Transactional(rollbackFor = Exception.class)
    public void save(RedEnvelopeDTO redPackageDTO) {
        // 1. 红包 summary 存入数据库
        String redPackageNo = UUID.randomUUID().toString();
        RedEnvelopeEntity redPackageEntity = new RedEnvelopeEntity()
                .setRedPackageNo(redPackageNo)
                .setTotalAmount(Integer.valueOf(redPackageDTO.getTotalAmount()))
                .setTotalNumber(Integer.parseInt(redPackageDTO.getTotalNumber()))
                .setUserNo(redPackageDTO.getUserNo());
        redEnvelopeMapper.save(redPackageEntity);

        // 4. 缓存红包总金额
        stringRedisTemplate.opsForValue().set(RED_PACKAGE_KEY + redPackageNo, redPackageDTO.getTotalNumber(), 24, TimeUnit.HOURS);

        // 5. 以 LIST 数据结构存入 redis
        stringRedisTemplate.opsForList().leftPushAll(RED_PACKAGE_LIST_KEY + redPackageNo, "100", "200", "300");
    }

    private void saveRedPackageDetail(String redPackageNo, String userNo, Integer amount) {
        RedEnvelopeRecordEntity redPackageDetailEntity = new RedEnvelopeRecordEntity()
                .setRedEnvelopeNo(redPackageNo)
                .setUserNo(userNo)
                .setAmount(amount);
        redEnvelopeRecordMapper.save(redPackageDetailEntity);
    }

    private List<Integer> divideRedPackage(String totalAmount, String totalNumbers) {
        // 具体金额算法忽略，目前写死。单位为 分
        return Arrays.asList(100, 200, 300);
    }

    public RedEnvelopeVO get(String userNo, String redPackageNo) throws Exception {
        RedEnvelopeRecordEntity redPackageDetailEntity = redEnvelopeRecordMapper.selectByUserNo(userNo, redPackageNo);
        if (null != redPackageDetailEntity) {
            throw new Exception("您已抢过红包，不能重复抢红包");
        }

        String value = (String) stringRedisTemplate.opsForValue().get(RED_PACKAGE_KEY + redPackageNo);
        log.info("value: {}", value);
        if (StringUtils.isEmpty(value)) {
            throw new Exception("红包已抢完");
        }
        int totalNumber = Integer.parseInt(value);
        if (totalNumber <= 0) {
            throw new Exception("红包已抢完");
        }
        // 2. 随机选出红包金额
        String amount = (String) stringRedisTemplate.opsForList().rightPop(RED_PACKAGE_LIST_KEY + redPackageNo);

        // 3. 红包 detail 存入数据库
        saveRedPackageDetail(redPackageNo, userNo, Integer.parseInt(amount));

        // 4. 更新缓存
        totalNumber = totalNumber - 1;
        if (totalNumber <= 0) {
            stringRedisTemplate.opsForValue().getOperations().delete(RED_PACKAGE_KEY + redPackageNo);
        } else {
            stringRedisTemplate.opsForValue().set(RED_PACKAGE_KEY + redPackageNo, String.valueOf(totalNumber));
        }

        // 5. 根据需要显示前端必要的数据信息
        return new RedEnvelopeVO().setUserNo(userNo).setAmount(Integer.parseInt(amount)).setCreatedTime(LocalDateTime.now());
    }

    public void getWithLua(String redEnvelopeNo, String userNo) throws Exception {
        String userNos = userNo + "-" + System.currentTimeMillis();
        // 执行 lua 脚本
        Object res = stringRedisTemplate.execute((RedisConnection connection) -> connection.eval(
                redisScript.getScriptAsString().getBytes(), ReturnType.INTEGER, 1, redEnvelopeNo.getBytes(), userNos.getBytes()));
        int result = Integer.parseInt(String.valueOf(res));
        log.info("result: {}", result);
        if (result == 0) {
            throw new Exception("红包已抢完");
        }
        // lua 脚本返回
        // 返回 2 时为最后一个红包，此时将抢红包信息通过异步保存到数据库中
        if (result == 2) {
            String unitAmount = (String) stringRedisTemplate.opsForHash().get("red_envelope_" + redEnvelopeNo, "unit_amount");
            saveUserRedPacketByRedis(redEnvelopeNo, Integer.parseInt(unitAmount));
        }
    }

    @Async
    public void saveUserRedPacketByRedis(String redEnvelopeNo, int unitAmount) {
        log.info("----------异步：开始保存用户抢红包记录到数据库----------------");
        long start = System.currentTimeMillis();
        String key = PREFIX + redEnvelopeNo;
        BoundListOperations<String, Object> ops = stringRedisTemplate.boundListOps(key);
        List<Object> userIdList = ops.range(0, -1);
        if (userIdList == null) {
            return;
        }
        List<RedEnvelopeRecordEntity> userRedPackets = new ArrayList<>();
        for (Object o : userIdList) {
            String values = o.toString();
            String[] arr = values.split("-");
            String userId = arr[0];
            RedEnvelopeRecordEntity userRedPacket = new RedEnvelopeRecordEntity();
            userRedPacket.setRedEnvelopeNo(redEnvelopeNo);
            userRedPacket.setUserNo(userId);
            userRedPacket.setAmount(unitAmount);
            userRedPacket.setMemo("抢红包 " + redEnvelopeNo);
            userRedPackets.add(userRedPacket);
        }
        executeBatch(userRedPackets);
        stringRedisTemplate.delete(key);
        long end = System.currentTimeMillis();
        System.out.println("保存数据结束，耗时" + (end - start) + "毫秒，共" + userIdList.size() + "条记录被保存");
    }

    private void executeBatch(List<RedEnvelopeRecordEntity> userRedPackets) {
        //  这里可以通过批量处理实现
        for (RedEnvelopeRecordEntity userRedPacket : userRedPackets) {
            redEnvelopeMapper.decreaseStock(userRedPacket.getRedEnvelopeNo());
            redEnvelopeRecordMapper.add(userRedPacket);
        }
    }

    public void getWithVersion(String redEnvelopeNo, String userNo) throws Exception {
        // 乐观锁通过限制时间或者次数实现，没有使用 redis 缓存
        long start = System.currentTimeMillis();
        while (true) {
            long end = System.currentTimeMillis();
            if (end - start > 100) {
                return;
            }
            RedEnvelopeEntity redEnvelopeEntity = redEnvelopeMapper.selectByRedEnvelopeNo(redEnvelopeNo);
            if (null == redEnvelopeEntity) {
                throw new Exception("红包不存在");
            }
            Integer id = redEnvelopeEntity.getId();
            if (redEnvelopeEntity.getStock() > 0) {
                int updated = redEnvelopeMapper.decreaseStockWithVersion(id, redEnvelopeEntity.getVersion());
                if (updated == 0) {
                    continue;
                }
                redEnvelopeRecordMapper.add(new RedEnvelopeRecordEntity().setUserNo(userNo).setRedEnvelopeNo(redEnvelopeNo));
            } else {
                return;
            }
        }
    }
}

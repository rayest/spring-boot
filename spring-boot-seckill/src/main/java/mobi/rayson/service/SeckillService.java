package mobi.rayson.service;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import mobi.rayson.common.SeckillStatEnum;
import mobi.rayson.mapper.SeckillMapper;
import mobi.rayson.mapper.SuccessKilledMapper;
import mobi.rayson.model.SuccessKilled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/***
 *  Created with IntelliJ IDEA.
 *  User:  lirui
 *  Date:  2019-03-11
 *  Time: 1:18 PM
 *  Description:
 **/
@Service
@Slf4j
public class SeckillService {

  private Lock lock = new ReentrantLock(true);

  private static int corePoolSize = Runtime.getRuntime().availableProcessors();
  //创建线程池  调整队列数 拒绝服务
  private static ThreadPoolExecutor executor =
      new ThreadPoolExecutor(corePoolSize,
          corePoolSize + 1,
          10l,
          TimeUnit.SECONDS,
          new LinkedBlockingQueue<>(1000));

  @Resource
  private SeckillMapper seckillMapper;

  @Resource
  private SuccessKilledMapper successKilledMapper;

  public void seckillOne(long seckillId) {
    int skillNum = 1000; // 1000 个购买者
    final CountDownLatch latch = new CountDownLatch(skillNum);
    log.info("开始秒杀一，可能会出现超卖");
    for (int i = 0; i < skillNum; i++) {
      final long userId = i;
      Runnable task = () -> {
        try {
          startSeckillOne(seckillId, userId);
        } catch (Exception e) {
          e.printStackTrace();
        }
        latch.countDown();
      };
      executor.execute(task);
    }
    try {
      latch.await();
      long seckillCount = getSeckillCount(seckillId);
      log.info("一共秒杀{}件商品", seckillCount);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  private long getSeckillCount(long seckillId) {
    return successKilledMapper.countBySeckillId(seckillId);
  }

  @Transactional
  void startSeckillOne(long seckillId, long userId) throws Exception {
    handleSeckill(seckillId, userId);
  }

  private void handleSeckill(long seckillId, long userId) throws Exception {
    // 校验库存
    long number = seckillMapper.countLeft(seckillId);
    if (number <= 0) {
      throw new Exception(SeckillStatEnum.END.getInfo());
    }

    // 扣库存
    seckillMapper.updateNumber(seckillId);

    // 创建订单
    SuccessKilled successKilled = SuccessKilled.builder()
        .seckillId(seckillId)
        .userId(userId)
        .build();
    successKilledMapper.save(successKilled);
  }

  public void seckillTwo(long seckillId) {
    int seckillNum = 1000;
    final CountDownLatch latch = new CountDownLatch(seckillNum);
    for (int i = 0; i < seckillNum; i++) {
      final long userId = i;
      Runnable task = () -> {
        startSeckillTwo(seckillId, userId);
        latch.countDown();
      };
      executor.execute(task);
    }

    try {
      latch.await();
      long seckillCount = getSeckillCount(seckillId);
      log.info("一共秒杀{}件商品", seckillCount);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  private void startSeckillTwo(long seckillId, long userId) {
    try {
      lock.lock();
      long number = seckillMapper.countLeft(seckillId);
      if (number <= 0) {
        throw new Exception(SeckillStatEnum.END.getInfo());
      }
      handleSeckill(seckillId, userId);
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      lock.unlock();
    }
  }
}

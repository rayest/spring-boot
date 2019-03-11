package mobi.rayson.service;

import io.swagger.annotations.ApiOperation;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import mobi.rayson.common.SeckillStatEnum;
import mobi.rayson.common.aspect.ServiceLimit;
import mobi.rayson.common.aspect.ServiceLock;
import mobi.rayson.common.queue.SeckillQueue;
import mobi.rayson.mapper.SeckillMapper;
import mobi.rayson.mapper.SuccessKilledMapper;
import mobi.rayson.model.Seckill;
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
  public void startSeckillOne(long seckillId, long userId) throws Exception {
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

  @ApiOperation("使用可重入锁和AOP实现")
  @ServiceLock
  @Transactional
  public void startSeckillTwo(long seckillId, long userId) {
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

  public void seckillThree(long seckillId) {
    int seckillNumber = 1000;
    final CountDownLatch latch = new CountDownLatch(seckillNumber);
    for (int i = 0; i < 1000; i++) {
      final long userId = i;
      Runnable task = () -> {
        try {
          startSeckillThree(seckillId, userId);
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

  @Transactional
  @ServiceLimit
  public void startSeckillThree(long seckillId, long userId) throws Exception {
    // MySQL 的悲观锁的用法：在 select 语句后加 "FOR UPDATE"
    long number = seckillMapper.selectNumberWithPessimisticLock(seckillId);
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

  public void seckillFour(long seckillId) {
    int seckillNumber = 1000;
    final CountDownLatch latch = new CountDownLatch(seckillNumber);
    for (int i = 0; i < 1000; i++) {
      final long userId = i;
      Runnable task = () -> {
        try {
          startSeckillFour(seckillId, userId, 1);
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

  @Transactional
  void startSeckillFour(long seckillId, long userId, int number) throws Exception {
    // 剩余的数量应该要大于等于秒杀的数量
    Seckill seckill = seckillMapper.selectBySeckillId(seckillId);
    if (seckill.getNumber() < number) {
      throw new Exception(SeckillStatEnum.END.getInfo());
    }

    // 乐观锁：通常是通过 version 版本变化或者 number 数量变化等实现
    int count = seckillMapper.update(seckillId, seckill.getVersion(), number);
    if (count <= 0) {
      throw new Exception(SeckillStatEnum.END.getInfo());
    }

    // 创建订单
    SuccessKilled successKilled = SuccessKilled.builder()
        .seckillId(seckillId)
        .userId(userId)
        .build();
    successKilledMapper.save(successKilled);
  }

  public void seckillFive(long seckillId) {
    for (int i = 0; i < 1000; i++) {
      final long userId = i;

      Runnable task = () -> {
        SuccessKilled kill = new SuccessKilled();
        kill.setSeckillId(seckillId);
        kill.setUserId(userId);
        try {
          Boolean flag = SeckillQueue.getMailQueue().produce(kill);
          if (flag) {
            log.info("用户:{} 秒杀成功", kill.getUserId());
          } else {
            log.info("用户:{} 秒杀失败", userId);
          }
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      };
      executor.execute(task);
    }
    try {
      Thread.sleep(10000);
      Long seckillCount = getSeckillCount(seckillId);
      log.info("一共秒杀出{}件商品", seckillCount);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}

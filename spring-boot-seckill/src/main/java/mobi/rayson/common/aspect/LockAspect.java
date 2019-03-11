package mobi.rayson.common.aspect;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/***
 *  Created with IntelliJ IDEA.
 *  User:  lirui
 *  Date:  2019-03-11
 *  Time: 3:55 PM
 *  Description:
 **/
@Component
@Order(1)
@Aspect
@Scope
public class LockAspect {

  private static Lock lock = new ReentrantLock(true);

  // 切点：在哪些类，哪些方法上切入（where)
  @Pointcut("@annotation(mobi.rayson.common.aspect.ServiceLock)")
  public void lockAspect() {
  }

  // 环绕通知
  @Around("lockAspect()")
  public Object around(ProceedingJoinPoint joinPoint) {
    lock.lock(); // 类似于在 @Before 处处理
    Object object;
    try {
      object = joinPoint.proceed();
    } catch (Throwable throwable) {
      throwable.printStackTrace();
      throw new RuntimeException();
    } finally {
      lock.unlock(); // 类似于在 @After 处处理
    }
    return object;
  }
}

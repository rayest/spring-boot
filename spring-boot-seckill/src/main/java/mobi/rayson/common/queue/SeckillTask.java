package mobi.rayson.common.queue;

import mobi.rayson.model.SuccessKilled;
import mobi.rayson.service.SeckillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/***
 *  Created with IntelliJ IDEA.
 *  User:  lirui
 *  Date:  2019-03-11
 *  Time: 10:53 PM
 *  Description:
 **/
@Component
public class SeckillTask implements ApplicationRunner {

  @Autowired
  private SeckillService seckillService;

  @Override
  public void run(ApplicationArguments var) throws Exception {
    while (true) {
      //进程内队列
      SuccessKilled kill = SeckillQueue.getMailQueue().consume();
      if (kill != null) {
        seckillService.startSeckillOne(kill.getSeckillId(), kill.getUserId());
      }
    }
  }
}
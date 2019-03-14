package mobi.rayson.factory;

import javax.annotation.Resource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/***
 *  Created with IntelliJ IDEA.
 *  User:  lirui
 *  Date:  2019-03-14
 *  Time: 6:09 PM
 *  Description:
 **/
@Component
public class FactoryRunner implements CommandLineRunner {

  @Resource
  private PayService payService;

  @Override
  public void run(String... args) throws Exception {
      PayRequest payRequest = new PayRequest();
      payRequest.setPayChannelType("wechat");
      payService.pay(payRequest);
  }
}

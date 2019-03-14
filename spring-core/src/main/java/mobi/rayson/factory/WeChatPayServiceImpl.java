package mobi.rayson.factory;

import javax.annotation.PostConstruct;
import org.springframework.stereotype.Service;

/***
 *  Created with IntelliJ IDEA.
 *  User:  lirui
 *  Date:  2019-03-14
 *  Time: 6:02 PM
 *  Description:
 **/
@Service
public class WeChatPayServiceImpl implements PayChannelService {

  @PostConstruct
  public void init() {
    PayChannelServiceFactory.registerPayService(PayChannel.WECHAT, this);
  }

  @Override
  public void pay(PayTransaction payTransaction) {
    System.out.println("开始处理微信支付");
  }
}

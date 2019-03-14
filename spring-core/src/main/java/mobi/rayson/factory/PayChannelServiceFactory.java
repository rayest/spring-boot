package mobi.rayson.factory;

import java.util.HashMap;
import java.util.Map;

/***
 *  Created with IntelliJ IDEA.
 *  User:  lirui
 *  Date:  2019-03-14
 *  Time: 5:44 PM
 *  Description:
 **/
public class PayChannelServiceFactory {

  public static Map<String, PayChannelService> payWays = new HashMap<>();

  public static void registerPayService(PayChannel payChannelType, PayChannelService service) {
    if (payWays.get(payChannelType.getCode()) != null) {
      throw new IllegalArgumentException("已经存在");
    }
    payWays.put(payChannelType.getCode(), service);
  }

  public static PayChannelService getPayService(String payChannelType) throws Exception {
    PayChannel payChannel = PayChannel.getPayChannel(payChannelType);
    PayChannelService payChannelService = payWays.get(payChannel.getCode());
    if (payChannelService == null) {
      throw new Exception("支付方式没有实现");
    }
    return payChannelService;
  }
}

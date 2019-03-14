package mobi.rayson.factory;

import org.springframework.stereotype.Service;

/***
 *  Created with IntelliJ IDEA.
 *  User:  lirui
 *  Date:  2019-03-14
 *  Time: 5:38 PM
 *  Description:
 **/
@Service
public class PayService {
  public void pay(PayRequest payRequest) throws Exception {
    String payChannelType = payRequest.getPayChannelType();
    PayChannelService payChannelService = PayChannelServiceFactory.getPayService(payChannelType);
    PayTransaction payTransaction = new PayTransaction();
    payTransaction.setTransactionId(1);
    payChannelService.pay(payTransaction);
  }
}

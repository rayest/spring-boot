package mobi.rayson.factory;

/***
 *  Created with IntelliJ IDEA.
 *  User:  lirui
 *  Date:  2019-03-14
 *  Time: 5:40 PM
 *  Description:
 **/
public enum PayChannel {
  ALIPAY("支付宝"),
  WECHAT("微信");
  private String msg;

  PayChannel(String msg) {
    this.msg = msg;
  }

  public String getCode() {
    return name().toLowerCase();
  }

  public String getMsg() {
    return msg;
  }

  public static PayChannel getPayChannel(String payChannelType) throws Exception {
    for (PayChannel payChannel : values()) {
      if (payChannel.getCode().equals(payChannelType)) {
        return payChannel;
      }
    }
    throw new Exception("支付方式不存在");
  }
}

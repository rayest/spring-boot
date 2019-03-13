package mobi.rayson.event;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/***
 *  Created with IntelliJ IDEA.
 *  User:  lirui
 *  Date:  2018-12-30
 *  Time: 11:56 AM
 *  Description:
 **/
@Component
public class UserListener implements ApplicationListener<UserEvent> {

  public void onApplicationEvent(UserEvent event) {
    String msg = event.getMsg();
    System.out.println("接收到了消息：" + msg);
  }
}

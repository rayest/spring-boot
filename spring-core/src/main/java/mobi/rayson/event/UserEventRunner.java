package mobi.rayson.event;

import javax.annotation.Resource;
import org.springframework.boot.CommandLineRunner;

/***
 *  Created with IntelliJ IDEA.
 *  User:  lirui
 *  Date:  2019-03-13
 *  Time: 3:31 PM
 *  Description:
 **/
//@Component
public class UserEventRunner implements CommandLineRunner {

  @Resource
  private UserPublisher userPublisher;

  @Override
  public void run(String... args) {
    userPublisher.publish("Hello Application Context!");
  }
}

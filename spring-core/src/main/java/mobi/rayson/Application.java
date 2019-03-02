package mobi.rayson;

import javax.annotation.Resource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/***
 *  Created with IntelliJ IDEA.
 *  User:  lirui
 *  Date:  2018-12-29
 *  Time: 10:29 AM
 *  Description:
 **/
@SpringBootApplication
public class Application implements CommandLineRunner {

  @Resource
  private UserPublisher userPublisher;

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    userPublisher.publish("Hello Application Context!");
  }
}

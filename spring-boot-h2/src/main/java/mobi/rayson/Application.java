package mobi.rayson;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/***
 *  Created with IntelliJ IDEA.
 *  User:  lirui
 *  Date:  2018-12-29
 *  Time: 10:29 AM
 *  Description:
 **/
@SpringBootApplication
@EnableTransactionManagement(mode = AdviceMode.PROXY)
public class Application {
  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }
}
package mobi.rayson.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@EnableGlobalMethodSecurity(securedEnabled = true)
@EnableCaching
public class AuthServerApplication {

  public static void main(String[] args) {
    SpringApplication.run(AuthServerApplication.class, args);
  }
}

package mobi.rayson.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/***
 *  Created with IntelliJ IDEA.
 *  User:  lirui
 *  Date:  2019-01-17
 *  Time: 2:44 PM
 *  Description:
 **/
@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

  @Bean
  @Override
  protected UserDetailsService userDetailsService() {
    InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
    manager.createUser(User.withUsername("user_1").password("123456").authorities("USER").build());
    manager.createUser(User.withUsername("user_2").password("123456").authorities("USER").build());
    return manager;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    // @formatter:off
    http
        .requestMatchers().anyRequest()
        .and()
        .authorizeRequests()
        .antMatchers("/oauth/*").permitAll();
    // @formatter:on
  }

  /**
   * 这一步的配置是必不可少的，否则SpringBoot会自动配置一个AuthenticationManager,覆盖掉内存中的用户
   */
  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @SuppressWarnings("deprecation")
  @Bean
  public static NoOpPasswordEncoder passwordEncoder() {
    return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
  }
}

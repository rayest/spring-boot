package mobi.rayson.auth.configure;

import javax.annotation.Resource;
import mobi.rayson.auth.exception.WebResponseExceptionTranslator;
import mobi.rayson.auth.foundation.integration.IntegrationAuthenticationFilter;
import mobi.rayson.auth.foundation.DataSourceCacheClientDetailService;
import mobi.rayson.auth.foundation.security.AuthUserDetailService;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

@EnableAuthorizationServer
@Configuration
public class AuthorizationServerConfigure extends AuthorizationServerConfigurerAdapter {

  @Resource
  private RedisTokenStore tokenStore;

  @Resource
  private AuthenticationManager authenticationManager;

  @Resource
  private IntegrationAuthenticationFilter integrationAuthenticationFilter;

  @Resource
  private AuthUserDetailService userDetailService;

  @Resource
  private DataSourceCacheClientDetailService cacheClientDetailService;

  @Resource
  private WebResponseExceptionTranslator webResponseExceptionTranslator;

  @Override
  public void configure(AuthorizationServerSecurityConfigurer security) {
    security
        .tokenKeyAccess("permitAll()")
        .checkTokenAccess("isAuthenticated()")
        /*允许表单方式提交客户端认证信息*/
        .allowFormAuthenticationForClients()
        .addTokenEndpointAuthenticationFilter(integrationAuthenticationFilter);
  }

  @Override
  public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
    endpoints
        .tokenStore(tokenStore)
        .authenticationManager(authenticationManager)
        .userDetailsService(userDetailService)
        .reuseRefreshTokens(false)
        .exceptionTranslator(webResponseExceptionTranslator);
  }

  @Override
  public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    clients.withClientDetails(cacheClientDetailService);
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public FilterRegistrationBean filterRegistrationBean(IntegrationAuthenticationFilter filter) {
    FilterRegistrationBean registration = new FilterRegistrationBean();
    registration.setFilter(filter);
    registration.setEnabled(false);
    return registration;
  }
}

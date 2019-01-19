package mobi.rayson.auth.configure;

import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import mobi.rayson.auth.exception.WebResponseExceptionTranslator;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationManager;
import org.springframework.security.oauth2.provider.authentication.OAuth2AuthenticationProcessingFilter;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

@Configuration
@EnableResourceServer
@Slf4j
public class ResourceServerConfigure extends ResourceServerConfigurerAdapter {

  @Resource
  private TokenStore tokenStore;

  @Resource
  private WebResponseExceptionTranslator exceptionTranslator;

  @Override
  public void configure(HttpSecurity http) throws Exception {

    OAuth2AuthenticationEntryPoint authenticationEntryPoint = new OAuth2AuthenticationEntryPoint();
    authenticationEntryPoint.setExceptionTranslator(exceptionTranslator);

    DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
    defaultTokenServices.setTokenStore(tokenStore);

    OAuth2AuthenticationManager oAuth2AuthenticationManager = new OAuth2AuthenticationManager();
    oAuth2AuthenticationManager.setTokenServices(defaultTokenServices);

    OAuth2AuthenticationProcessingFilter filter = new OAuth2AuthenticationProcessingFilter();
    filter.setAuthenticationEntryPoint(authenticationEntryPoint);
    filter.setAuthenticationManager(oAuth2AuthenticationManager);

    http.csrf().disable()
        .httpBasic().disable()
        .authorizeRequests().antMatchers("/oauth/*", "/health").permitAll()
        .anyRequest().authenticated()
        .and().logout().permitAll()
        .and().formLogin().permitAll()
        .and().addFilterBefore(filter, AbstractPreAuthenticatedProcessingFilter.class);
  }
}

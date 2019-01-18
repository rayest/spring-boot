package mobi.rayson.configuration;

import javax.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/***
 *  Created with IntelliJ IDEA.
 *  User:  lirui
 *  Date:  2019-01-18
 *  Time: 5:12 PM
 *  Description:
 **/
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {
  private static final String ORDER_RESOURCE_ID = "order";

  @Resource
  private AuthenticationManager authenticationManager;
  @Resource
  private RedisConnectionFactory redisConnectionFactory;

  @Override
  public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
    //配置两个客户端,一个用于password认证一个用于client认证
    clients.inMemory()
        .withClient("client_1")
        .resourceIds(ORDER_RESOURCE_ID)
        .authorizedGrantTypes("client_credentials", "refresh_token")
        .scopes("select")
        .authorities("client")
        .secret("123456")
        .and()
        .withClient("client_2")
        .resourceIds(ORDER_RESOURCE_ID)
        .authorizedGrantTypes("password", "refresh_token")
        .scopes("select")
        .authorities("client")
        .secret("123456");
  }

  @Override
  public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
    endpoints
        .tokenStore(new RedisTokenStore(redisConnectionFactory))
        // 2018-4-3 增加配置，允许 GET、POST 请求获取 token，即访问端点：oauth/token
        .authenticationManager(authenticationManager)
        .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);
  }

  @Override
  public void configure(AuthorizationServerSecurityConfigurer oauthServer) {
    //允许表单认证
    oauthServer.allowFormAuthenticationForClients();
  }
}

package mobi.rayson.auth.configure;

import javax.annotation.Resource;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

@Configuration
public class DataSourceConfigure {

  @Resource
  private RedisConnectionFactory connectionFactory;

  @Bean
  public RedisTokenStore tokenStore() {
    return new RedisTokenStore(connectionFactory);
  }

  @Bean(name = "JdbcClientDetailsService4Wrapper")
  public JdbcClientDetailsService clientDetailsService(DataSource dataSource) {
    return new JdbcClientDetailsService(dataSource);
  }
}

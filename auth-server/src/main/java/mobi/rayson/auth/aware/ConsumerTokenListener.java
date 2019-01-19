package mobi.rayson.auth.aware;

import java.util.Optional;
import javax.annotation.Resource;
import mobi.rayson.auth.event.RevokeTokenEvent;
import mobi.rayson.auth.event.RevokeTokenEventEntry;
import org.springframework.context.ApplicationListener;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Component
public class ConsumerTokenListener implements ApplicationListener<RevokeTokenEvent> {

  @Resource
  private RedisTokenStore tokenStore;

  @Override
  public void onApplicationEvent(RevokeTokenEvent event) {
    RevokeTokenEventEntry entry = (RevokeTokenEventEntry) event.getSource();
    if (ObjectUtils.isEmpty(entry)) {
      return;
    }
    tokenStore
        .findTokensByClientIdAndUserName(entry.getClientId(), entry.getUserName())
        .forEach(oAuth2AccessToken ->
            {
              if (Optional.ofNullable(oAuth2AccessToken.getValue()).isPresent()) {
                tokenStore.removeAccessToken(oAuth2AccessToken.getValue());
              }
              if (Optional.ofNullable(oAuth2AccessToken.getRefreshToken()).isPresent()) {
                tokenStore.removeRefreshToken(oAuth2AccessToken.getRefreshToken());
              }
            }
        );
  }
}

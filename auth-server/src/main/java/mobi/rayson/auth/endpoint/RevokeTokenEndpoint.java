package mobi.rayson.auth.endpoint;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import java.security.Principal;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import mobi.rayson.auth.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class RevokeTokenEndpoint {

  @Resource
  private RedisTokenStore tokenStore;

  @Resource
  private AuthorizationServerTokenServices authorizationServerTokenServices;

  @Resource
  private UserService userService;

  @DeleteMapping(value = "/revoke/token")
  @ResponseBody
  public ResponseEntity revokeToken(Principal principal) {
    if (principal instanceof OAuth2Authentication) {
      OAuth2Authentication auth2Authentication = (OAuth2Authentication) principal;
      OAuth2AccessToken token = authorizationServerTokenServices
          .getAccessToken(auth2Authentication);
      try {
        String jsonString =
            JSONObject.toJSONString(auth2Authentication.getPrincipal());
        JSONObject detail = JSON.parseObject(jsonString);
        String userId = detail.getString("username");
        userService.deleteDevice(userId);
      } catch (Exception e) {
        e.printStackTrace();
      }

      tokenStore.removeAccessToken(token);
      return ResponseEntity.ok().build();
    }
    return null;
  }
}


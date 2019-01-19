package mobi.rayson.auth.foundation.security;

import java.util.List;
import mobi.rayson.auth.domain.SysUserAuthentication;
import mobi.rayson.auth.domain.User;
import mobi.rayson.auth.foundation.integration.IntegrationAuthenicator;
import mobi.rayson.auth.foundation.integration.IntegrationAuthentication;
import mobi.rayson.auth.foundation.integration.IntegrationAuthenticationContext;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/***
 *  Description: 根据用户userid找到用户模型
 *
 *  Spring MVC会触发AuthenticationProvider的authenticate(Authentication authentication)方法，
 *  其中Authentication包含了前段页面输入的账号和密码。
 * authenticate(Authentication authentication)根据authentication.getName()获取用户名，
 * 然后根据UserDetailsService的loadUserByUsername(String username)方法，获取用户的UserDetails，
 * 其中返回的UserDetails包含了服务器已经保存好的用户名、密码和对应的权限。
 * 将用户输入的用户名和密码(包含在authentication里)和服务器已经保存好的UserDetails进行对比，如果匹配成功，
 * 返回一个UsernamePasswordAuthenticationToken()，否则报异常或者返回null。
 **/
@Service
public class AuthUserDetailService implements UserDetailsService {

  private List<IntegrationAuthenicator> authenticators;

  @Autowired(required = false)
  public void setAuthenticators(
      List<IntegrationAuthenicator> authenticators) {
    this.authenticators = authenticators;
  }

  @Override
  public User loadUserByUsername(String username) throws UsernameNotFoundException {
    IntegrationAuthentication authentication = IntegrationAuthenticationContext.get();
    //判断是否是集成登录
    if (authentication == null) {
      authentication = new IntegrationAuthentication();
    }
    authentication.setUserName(username);

    SysUserAuthentication userAuthentication = authenticate(authentication);

    if (userAuthentication == null) {
      throw new UsernameNotFoundException("用户名或密码错误");
    }

    User user = new User();
    BeanUtils.copyProperties(userAuthentication, user);
    return user;
  }

  private SysUserAuthentication authenticate(IntegrationAuthentication integrationAuthentication) {
    if (this.authenticators != null) {
      for (IntegrationAuthenicator authenticator : authenticators) {
        if (authenticator.support(integrationAuthentication)) {
          return authenticator.authenticate(integrationAuthentication);
        }
      }
    }
    return null;
  }
}

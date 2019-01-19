package mobi.rayson.auth.foundation.integration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import mobi.rayson.auth.constant.Constant;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

/***
 *  Created with IntelliJ IDEA.
 *  @author :  xiamo
 *  Date:  2018-05-11
 *  Time: 09:51
 *  Description:
 **/
@Slf4j
@Component
public class IntegrationAuthenticationFilter extends GenericFilterBean implements
    ApplicationContextAware {

  /*自定义认证标示字段*/
  private static final String AUTH_TYPE_PARAM_NAME = "auth_type";

  private static final String OAUTH_TOKEN_URL = "/oauth/token";

  private RequestMatcher requestMatcher;

  private ApplicationContext applicationContext;

  private Collection<IntegrationAuthenicator> authenicators;

  public IntegrationAuthenticationFilter() {
    requestMatcher = new OrRequestMatcher(
        new AntPathRequestMatcher(OAUTH_TOKEN_URL, HttpMethod.GET.name()),
        new AntPathRequestMatcher(OAUTH_TOKEN_URL, HttpMethod.POST.name()));
  }

  @Override
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
      FilterChain filterChain)
      throws IOException, ServletException {
    HttpServletRequest request = (HttpServletRequest) servletRequest;
    HttpServletResponse response = (HttpServletResponse) servletResponse;

    /*如果匹配了认证规则，则进行拦截处理*/
    if (requestMatcher.matches(request)) {
      /*对满足要求的认证请求统统加入集成认证流程过滤器链中*/
      IntegrationAuthentication integrationAuthentication = new IntegrationAuthentication();
      integrationAuthentication.setAuthType(request.getParameter(AUTH_TYPE_PARAM_NAME));
      integrationAuthentication.setAuthParameters(request.getParameterMap());
      integrationAuthentication.setUserAgent(request.getHeader(Constant.USER_AGENT));

      IntegrationAuthenticationContext.set(integrationAuthentication);
      try {
        try {
          before(integrationAuthentication);
        } catch (RuntimeException e) {
          log.error("前置处理出现错误:", e);
          return;
        }
        filterChain.doFilter(request, response);
        try {
          after(integrationAuthentication);
        } catch (RuntimeException e) {
          log.error("后置处理出现错误:", e);
        }
      } finally {
        IntegrationAuthenticationContext.clear();
      }
    } else {
      filterChain.doFilter(request, response);
    }
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    this.applicationContext = applicationContext;
  }

  /*认证前进行预处理*/
  private void before(IntegrationAuthentication integrationAuthentication) {
    if (authenicators == null) {
      synchronized (this) {
        Map<String, IntegrationAuthenicator> integrationMap = null;
        try {
          integrationMap = applicationContext.getBeansOfType(IntegrationAuthenicator.class);
        } catch (BeansException e) {
          log.error("Could not found IntegrationAuthenicator implement.");
        }
        if (integrationMap != null) {
          authenicators = integrationMap.values();
        }
      }
    }

    if (authenicators == null) {
      authenicators = new ArrayList<>();
    }

    for (IntegrationAuthenicator authenicator : authenicators) {
      if (authenicator.support(integrationAuthentication)) {
        authenicator.prepare(integrationAuthentication);
      }
    }
  }

  /*认证结束后进行后置处理*/
  private void after(IntegrationAuthentication integrationAuthentication) {
    for (IntegrationAuthenicator authenicator : authenicators) {
      if (authenicator.support(integrationAuthentication)) {
        authenicator.complete(integrationAuthentication);
      }
    }
  }
}

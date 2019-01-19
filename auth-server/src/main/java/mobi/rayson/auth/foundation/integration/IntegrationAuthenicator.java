package mobi.rayson.auth.foundation.integration;

import mobi.rayson.auth.domain.SysUserAuthentication;

public interface IntegrationAuthenicator {

  /**
   * 处理集成认证
   */
  SysUserAuthentication authenticate(IntegrationAuthentication integrationAuthentication);


  /**
   * 进行预处理
   */
  void prepare(IntegrationAuthentication integrationAuthentication);

  /**
   * 判断是否支持集成认证类型
   */
  boolean support(IntegrationAuthentication integrationAuthentication);

  /**
   * 认证结束后执行
   */
  void complete(IntegrationAuthentication integrationAuthentication);


}

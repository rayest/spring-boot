package mobi.rayson.auth.foundation.integration;

import mobi.rayson.auth.domain.SysUserAuthentication;

public abstract class AbstractIntegrationAuthenicator implements IntegrationAuthenicator {

  @Override
  public abstract SysUserAuthentication authenticate(
      IntegrationAuthentication integrationAuthentication);

  @Override
  public abstract void prepare(IntegrationAuthentication integrationAuthentication);

  @Override
  public abstract boolean support(IntegrationAuthentication integrationAuthentication);

  @Override
  public void complete(IntegrationAuthentication integrationAuthentication) {

  }
}

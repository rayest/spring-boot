package mobi.rayson.auth.foundation.integration.authenicator;

import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import mobi.rayson.auth.service.SMSService;
import mobi.rayson.auth.service.UserService;
import mobi.rayson.auth.constant.Constant;
import mobi.rayson.auth.domain.RegistrationDTO;
import mobi.rayson.auth.domain.SysUserAuthentication;
import mobi.rayson.auth.event.RevokeTokenEvent;
import mobi.rayson.auth.event.RevokeTokenEventEntry;
import mobi.rayson.auth.foundation.integration.AbstractIntegrationAuthenicator;
import mobi.rayson.auth.foundation.integration.IntegrationAuthentication;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SmsAuthenticator extends AbstractIntegrationAuthenicator implements
    ApplicationEventPublisherAware {

  private final static String SMS_AUTH_TYPE = "sms";

  @Resource
  private PasswordEncoder passwordEncoder;

  @Resource
  private UserService userService;

  @Resource
  private SMSService smsService;

  private ApplicationEventPublisher applicationEventPublisher;

  @Override
  public SysUserAuthentication authenticate(IntegrationAuthentication integrationAuthentication) {

    /*获取用户名，此处是用户id*/
    String userName = integrationAuthentication.getUserName();
    /*获取用户UA，以确定用户的版本*/
    String userAgent = integrationAuthentication.getUserAgent();

    String deviceId = integrationAuthentication.getAuthParameters(Constant.DEVICE_ID);
    RegistrationDTO registrationDTO = new RegistrationDTO(userName, deviceId, userAgent);
    SysUserAuthentication sysUserAuthentication = userService.register(registrationDTO);

    if (sysUserAuthentication == null) {
      log.error("访问用户服务发生错误:");
      return null;
    }
    /*revoke token last time.*/
    RevokeTokenEventEntry revokeTokenEventEntry = new RevokeTokenEventEntry(userName, deviceId);
    RevokeTokenEvent revokeTokenEvent = new RevokeTokenEvent(revokeTokenEventEntry);
    applicationEventPublisher.publishEvent(revokeTokenEvent);
    /*获取密码，实际值是验证码*/
    String password = integrationAuthentication.getAuthParameters("password");
    sysUserAuthentication.setPassword(passwordEncoder.encode(password));
    return sysUserAuthentication;
  }

  @Override
  public void prepare(IntegrationAuthentication integrationAuthentication) {
    String userName = integrationAuthentication.getAuthParameters("username");
    String password = integrationAuthentication.getAuthParameters("password");
    if (smsService.validateVCode(userName, password)) {
      log.error("验证码错误或者已经过期");
    }
  }

  @Override
  public boolean support(IntegrationAuthentication integrationAuthentication) {
    return SMS_AUTH_TYPE.equals(integrationAuthentication.getAuthType());
  }

  @Override
  public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
    this.applicationEventPublisher = applicationEventPublisher;
  }
}

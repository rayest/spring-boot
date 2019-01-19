package mobi.rayson.auth.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.stereotype.Component;

/**
 * 异常转换器
 */
@Component
@Slf4j
public class WebResponseExceptionTranslator implements
    org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator {

  /**
   * @param e spring security内部异常
   * @return 经过处理的异常信息
   * @throws Exception 通用异常
   */
  @Override
  public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {
    log.error("WebResponseExceptionTranslator Error : ", e);
    if (e instanceof OAuth2Exception) {
      OAuth2Exception exception = (OAuth2Exception) e;
      //noinspection unchecked
      return new ResponseEntity("", HttpStatus.valueOf(exception.getHttpErrorCode()));
    } else {
      //noinspection unchecked
      return new ResponseEntity("Method Not Allowed", HttpStatus.METHOD_NOT_ALLOWED);
    }
  }
}

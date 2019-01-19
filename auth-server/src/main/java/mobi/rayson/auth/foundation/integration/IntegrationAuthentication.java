package mobi.rayson.auth.foundation.integration;

import java.util.Arrays;
import java.util.Map;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
public class IntegrationAuthentication {

  private String userName;
  private String authType;
  private Map<String, String[]> authParameters;
  private String userAgent;

  public String getAuthParameters(String parameter) {
    String[] parameters = authParameters.get(parameter);
    if (parameters != null && parameters.length > 0) {
      log.debug("The parameter: {} has value size: {} and value data: {}", parameter,
          parameters.length, Arrays.toString(parameters));
      return parameters[0];
    }
    log.debug("The parameter: {} has no value");
    return null;
  }


}

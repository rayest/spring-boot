package mobi.rayson.auth.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.io.Serializable;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SysUserAuthentication implements Serializable {

  @JsonProperty("userId")
  private String userName;

  @JsonProperty("phone")
  private String phone;

  @JsonIgnore
  private String password;


}

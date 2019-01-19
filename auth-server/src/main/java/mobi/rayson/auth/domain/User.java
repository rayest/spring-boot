package mobi.rayson.auth.domain;

import java.io.Serializable;
import java.util.Collection;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


public class User implements UserDetails, Serializable {

  /*可作为userId*/
  @Setter
  private String userName;

  @Setter
  @Getter
  private String phone;

  /*在短信验证中，作为验证码*/
  @Setter
  private String password;

  /*账号状态，其值待定义*/
  @Setter
  @Getter
  private String status;

  /*账号类型，其值待定义*/
  @Setter
  @Getter
  private String type;


  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
  }

  @Override
  public String getPassword() {
    return this.password;
  }

  @Override
  public String getUsername() {
    return this.userName;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}

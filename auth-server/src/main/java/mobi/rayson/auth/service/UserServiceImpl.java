package mobi.rayson.auth.service;

import mobi.rayson.auth.domain.RegistrationDTO;
import mobi.rayson.auth.domain.SysUserAuthentication;
import org.springframework.stereotype.Service;

/***
 *  Created with IntelliJ IDEA.
 *  User:  lirui
 *  Date:  2019-01-19
 *  Time: 8:53 PM
 *  Description:
 **/
@Service
public class UserServiceImpl implements UserService {
  @Override public SysUserAuthentication register(RegistrationDTO registrationDTO) {
    return null;
  }

  @Override public void deleteDevice(String userId) {

  }
}

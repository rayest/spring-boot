package mobi.rayson.auth.service;

import mobi.rayson.auth.domain.SysUserAuthentication;
import mobi.rayson.auth.domain.RegistrationDTO;

public interface UserService {

  SysUserAuthentication register(RegistrationDTO registrationDTO);

  void deleteDevice(String userId);
}

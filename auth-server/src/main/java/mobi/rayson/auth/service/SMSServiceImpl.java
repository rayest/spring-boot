package mobi.rayson.auth.service;

import org.springframework.stereotype.Service;

/***
 *  Created with IntelliJ IDEA.
 *  User:  lirui
 *  Date:  2019-01-19
 *  Time: 8:53 PM
 *  Description:
 **/
@Service
public class SMSServiceImpl implements SMSService {
  @Override public boolean validateVCode(String phone, String vCode) {
    return false;
  }
}

package mobi.rayson.auth.service;

public interface SMSService {
  boolean validateVCode(String phone, String vCode);
}

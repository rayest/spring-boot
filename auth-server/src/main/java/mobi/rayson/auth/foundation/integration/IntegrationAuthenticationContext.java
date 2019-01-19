package mobi.rayson.auth.foundation.integration;

/***
 *  Description: 自定义集成登录方式的上下文
 **/
public class IntegrationAuthenticationContext {

  private final static ThreadLocal<IntegrationAuthentication> holder = new ThreadLocal<>();

  public static void set(IntegrationAuthentication integrationAuthentication) {
    holder.set(integrationAuthentication);
  }

  public static IntegrationAuthentication get() {
    return holder.get();
  }

  public static void clear() {
    holder.remove();
  }
}

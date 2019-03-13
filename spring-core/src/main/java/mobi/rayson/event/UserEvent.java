package mobi.rayson.event;

import org.springframework.context.ApplicationEvent;

/***
 *  Created with IntelliJ IDEA.
 *  User:  lirui
 *  Date:  2019-03-01
 *  Time: 5:36 PM
 *  Description:
 **/
public class UserEvent extends ApplicationEvent {

  private static final long serialVersionUID = 1L;
  private String msg;

  public UserEvent(Object source, String msg) {
    super(source);
    this.msg = msg;
  }

  public String getMsg() {
    return msg;
  }

  public void setMsg(String msg) {
    this.msg = msg;
  }
}
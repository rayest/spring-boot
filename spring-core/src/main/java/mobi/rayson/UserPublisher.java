package mobi.rayson;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.stereotype.Component;

/***
 *  Created with IntelliJ IDEA.
 *  User:  lirui
 *  Date:  2018-12-30
 *  Time: 11:21 AM
 *  Description:
 **/
@Component
public class UserPublisher implements ApplicationEventPublisherAware {

  private ApplicationEventPublisher applicationEventPublisher;

  public void publish(String msg) {
    applicationEventPublisher.publishEvent(new UserEvent(this, msg));
  }

  @Override
  public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
    this.applicationEventPublisher = applicationEventPublisher;
  }
}
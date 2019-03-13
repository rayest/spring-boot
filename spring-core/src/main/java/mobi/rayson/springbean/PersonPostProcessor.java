package mobi.rayson.springbean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

/***
 *  Created with IntelliJ IDEA.
 *  User:  lirui
 *  Date:  2019-03-13
 *  Time: 4:49 PM
 *  Description:
 **/
public class PersonPostProcessor implements BeanPostProcessor {

  public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
    System.out.println("postProcessBeforeInitialization 被调用");
    return bean;
  }

  public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
    System.out.println("postProcessAfterInitialization 被调用");
    return bean;
  }
}

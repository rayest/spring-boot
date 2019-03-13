package mobi.rayson.springbean;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/***
 *  Created with IntelliJ IDEA.
 *  User:  lirui
 *  Date:  2019-03-13
 *  Time: 3:41 PM
 *  Description:
 **/
@Configuration
public class BeanConfiguration {

  @Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
  @Bean(name = "person1", initMethod = "myInit", destroyMethod = "myDestroy")
  public Person person(){
    Person person1 = new Person();
    person1.setName("Jack");
    return person1;
  }
}

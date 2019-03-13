package mobi.rayson.springbean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/***
 *  Created with IntelliJ IDEA.
 *  User:  lirui
 *  Date:  2019-03-13
 *  Time: 3:33 PM
 *  Description:
 **/
public class Person implements BeanNameAware, BeanFactoryAware, ApplicationContextAware,
    InitializingBean, DisposableBean {

  private String name;

  public Person() {
    System.out.println("Person 类的构造方法");
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
    System.out.println("set 方法被调用");
  }

  @Override
  public String toString() {
    return "Person{" +
        "name='" + name + '\'' +
        '}';
  }

  //自定义的初始化函数
  public void myInit() {
    System.out.println("myInit 被调用");
  }

  //自定义的销毁方法
  public void myDestroy() {
    System.out.println("myDestroy 被调用");
  }

  @Override
  public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
    System.out.println("setBeanFactory 被调用");
    System.out.println("Has person1:" + beanFactory.containsBean("person1"));
  }

  @Override
  public void setBeanName(String name) {
    System.out.println("setBeanName 被调用。 beanName：" + name);
  }

  @Override
  public void destroy() throws Exception {
    System.out.println("destroy 被调用");
  }

  @Override
  public void afterPropertiesSet() throws Exception {
    System.out.println("afterPropertiesSet 被调用");
  }

  @Override
  public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
    System.out.println("setApplicationContext 被调用");
    System.out.println("Application ID: " + applicationContext.getId());
  }
}

package mobi.rayson;

import mobi.rayson.spring.User;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.Assert.assertEquals;

@SpringBootTest
public class ApplicationContextTest {

    private ApplicationContext applicationContext;

    @Before
    public void setUp() {
        applicationContext = new ClassPathXmlApplicationContext("bean.xml");
    }

    @Test
    public void test_getBean() {
        User user = (User) applicationContext.getBean("user");
        assertEquals("lee", user.getName());
    }

    @Test
    public void test_loadBeanDefinitions() {
        DefaultListableBeanFactory defaultListableBeanFactory = new DefaultListableBeanFactory(applicationContext);
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(defaultListableBeanFactory);
        int beanDefinitions = xmlBeanDefinitionReader.loadBeanDefinitions("bean.xml");
        assertEquals(1, beanDefinitions);
    }
}

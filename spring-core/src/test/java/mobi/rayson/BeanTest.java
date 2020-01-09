package mobi.rayson;

import mobi.rayson.spring.User;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.DefaultSingletonBeanRegistry;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;

import java.lang.reflect.Field;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@SpringBootTest
public class BeanTest {

    private ApplicationContext applicationContext;

    private DefaultListableBeanFactory defaultListableBeanFactory;

    @Before
    public void setUp() {
        applicationContext = new ClassPathXmlApplicationContext("bean.xml");
        defaultListableBeanFactory = new XmlBeanFactory(new ClassPathResource("bean.xml"));
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

    @Test
    public void test_singletonObjects() throws Exception {
        User user = (User) defaultListableBeanFactory.getBean("user");

        DefaultSingletonBeanRegistry defaultSingletonBeanRegistry = defaultListableBeanFactory;
        Class<?> clazz = Class.forName("org.springframework.beans.factory.support.DefaultSingletonBeanRegistry");

        Field singletonObjects = clazz.getDeclaredField("singletonObjects");
        singletonObjects.setAccessible(true);
        ConcurrentHashMap<String, Object> map = (ConcurrentHashMap<String, Object>) singletonObjects.get(defaultSingletonBeanRegistry);
        assertEquals(1, map.size());
        assertTrue(map.containsKey("user"));
    }
}

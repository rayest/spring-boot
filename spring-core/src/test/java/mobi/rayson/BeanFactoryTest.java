package mobi.rayson;

import mobi.rayson.common.Note;
import mobi.rayson.spring.User;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.config.SetFactoryBean;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.DefaultSingletonBeanRegistry;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@SpringBootTest
public class BeanFactoryTest {

    private DefaultListableBeanFactory defaultListableBeanFactory;
    private ClassPathResource classPathResource;

    @Before
    public void setUp() {
        classPathResource = new ClassPathResource("bean.xml");
        defaultListableBeanFactory = new XmlBeanFactory(classPathResource);
    }

    @Test
    @Note("利用反射获取单例缓存池中的 bean 对象")
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

    @Test
    @Note("FactoryBean 是一种特殊的bean")
    public void test_FactoryBean() {
        SetFactoryBean setFactoryBean = new SetFactoryBean();
        HashSet<String> sourceSet = new HashSet<>();
        Collections.addAll(sourceSet, "1", "2");
        setFactoryBean.setSourceSet(sourceSet);
        assertEquals(Set.class, setFactoryBean.getObjectType());
    }

    @Test
    public void test_BeanFactory() {

    }

    @Test
    public void test_classPathResource_path() throws Exception {

        Class<?> clazz = Class.forName("org.springframework.core.io.ClassPathResource");

        Field pathField = clazz.getDeclaredField("path");
        pathField.setAccessible(true);
        String path = (String) pathField.get(classPathResource);
        assertEquals("bean.xml", path);
    }

    @Test
    public void test_classPathResource_classLoader() throws Exception {
        Class<?> clazz = Class.forName("org.springframework.core.io.ClassPathResource");

        Field classLoaderField = clazz.getDeclaredField("classLoader");
        classLoaderField.setAccessible(true);
        ClassLoader classLoader = (ClassLoader) classLoaderField.get(classPathResource);
        assertEquals(ClassUtils.getDefaultClassLoader(), classLoader);
    }

    @Test
    public void test_() {

    }
}

package com.rayest;

import com.rayest.springboot.provider.RabbitProvider;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class SpringbootTest {

    @Resource
    private RabbitProvider provider;

    @Test
    public void test_provider_consumer() {
        provider.send("hello");
    }
}

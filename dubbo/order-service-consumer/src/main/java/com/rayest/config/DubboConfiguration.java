package com.rayest.config;

import com.alibaba.dubbo.config.ApplicationConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 也可以通过该 API 注入 的方式代替 XML 方式或者 application.yml 方式
//@Configuration
public class DubboConfiguration {

    @Bean
    public ApplicationConfig applicationConfig(){
        ApplicationConfig applicationConfig = new ApplicationConfig();
        applicationConfig.setName("order-service-consumer");
        return applicationConfig;
    }
}

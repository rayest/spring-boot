package com.rayest.config;

import org.springframework.context.annotation.ImportResource;

// 也可以通过该 API 注入 的方式代替 XML 方式或者 application.yml 方式

@org.springframework.context.annotation.Configuration
@ImportResource(locations={"classpath*:dubbo-consumer.xml"})
public class DubboConfiguration {
}

# springboot-dubbo

> 1. Springboot 整合 dubbo 实现 RPC
> 2. dubbo 和 springcloud区别：
>    1. springcloud 中服务之间调用通过 feign 实现。而 feign 调用是 HTTP 实现的，因此各个服务仍然需要提供接口API，以供其他啊服务调用。dubbo 是面向接口编程的。只有在网关 gateway 或者 bff 层需要提供接口 API，其他的服务仅需要实现作为***中间桥梁***的 common-api 接口即可，不需要单独提供API。

1. 启动 zookeeper

> 作为 dubbo 的注册发现中心服务

2. 启动 dubbo 后台管理服务以作为监控服务

> 1. 下载 dubbo-admin-master 安装包至目录 /Users/lirui/Documents/tools/for-code/dubbo-admin-master
> 2. 找到服务 dubbo-admin 并使用 maven 打包生成可执行 war 包文件，与 target/ 目录下
> 3. 执行 java -jar dubbo-admin-0.0.1-SNAPSHOT.jar，以启动
> 4. 请求 http://localhost:7001 查看服务状态等信息

3. 启动服务 user-service、order-service 

> 1. 编写 common-api 服务。并通过 maven install 安装到本地
> 2. 在 order-service 中通过 maven 依赖引入 common-api 服务
> 3. 启动 user-service 和 order-service

4. 服务之间的关系

> 1. common-api 定义接口
> 2. order-service 通过 @Reference 注解注入 common-api 服务定义的 UserService 接口
> 3. user-service 实现 common-api 服务定义的 UserService 接口，从而提供服务给 order-service

5. 健康检查

> 1. 在 user-service 中添加依赖
>
> ```java
> <dependency>
>     <groupId>org.springframework.boot</groupId>
>     <artifactId>spring-boot-starter-actuator</artifactId>
> </dependency>
> <dependency>
>     <groupId>org.apache.dubbo</groupId>
>     <artifactId>dubbo-spring-boot-actuator</artifactId>
>     <version>2.7.4.1</version>
> </dependency>
> ```
>
> 2. 访问：http://localhost:8081/actuator/health 

> 


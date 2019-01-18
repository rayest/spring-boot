# spring-boot-oauth2

## 使用 client 认证模式和 password 认证模式

* 配置完成并且成功启动后，服务将自动创建一些 endpoints：如 '/oauth/token' 等
> password 模式获取 token
>> http://localhost:1000/oauth/token?username=user_1&password=123456&grant_type=password&scope=select&client_id=client_2&client_secret=123456

> client 模式获取 token
>> http://localhost:1000/oauth/token?grant_type=client_credentials&scope=select&client_id=client_1&client_secret= 123456
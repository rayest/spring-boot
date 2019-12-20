# springboot-dubbo

> Springboot 整合 dubbo 实现 RPC

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

> 

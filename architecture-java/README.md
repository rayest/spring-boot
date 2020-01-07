## Java 架构实现

## 接口性能测试工具 jmeter
> 1. 安装 jmeter -> 启动GUI界面 -> 配置线程组、接口、参数、测试结果文件
> 2. 计算吞吐量(request/second)：总的线程数/持续时间

## 限流
### 应用级限流
* 限制总并发数/连接数/请求数 
> TPS/QPS。tomcat 控制
```yaml
server:
  tomcat:
    uri-encoding: utf-8
    accept-count: 700  # 可以放到处理队列中的请求数
    max-threads: 1000  # 最大并发数
    max-connections: 20000 # 接受和处理的最大连接数
    min-spare-threads: 20 # 初始化时创建的线程数
```

* 限制接口请求数/并发数
> 1. 通过 Semaphore 信号量许可，控制接口的并发数
> 2. 如果有可用的信号许可，则可以获取到并执行业务操作
> 3. 如果许可已被获取完，请求的线程被加入到等待队列中，直到有被释放的许可

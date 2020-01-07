## Java 架构实现

### 限流
* 限制总并发数/连接数/请求数 
> TPS/QPS。
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

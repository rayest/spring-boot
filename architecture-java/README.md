## Java 架构实现

## 接口性能测试工具 
* jmeter
> 1. 安装 jmeter -> 启动GUI界面 -> 配置线程组、接口、参数、测试结果文件
> 2. 计算吞吐量(request/second)：总的线程数/持续时间

* ab
> 模拟10个总请求数，10个并发
```bash
ab -n 10 -c 10 http://127.0.0.1:1001/film/id/1
```


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

* Semaphore 限制接口请求数/并发数
> 1. 通过 Semaphore 信号量许可，控制接口的并发数
> 2. 如果有可用的信号许可，则可以获取到并执行业务操作
> 3. 如果许可已被获取完，请求的线程被加入到等待队列中，直到有被释放的许可
> * Semaphore 原理
>> 1. Semaphore 通过底层的 AQS 定义了信号许可量，该许可量即为 AQS 的 state 值
>> 2. semaphore.acquire() 方法尝试获取 state，如果成功将 state 值减一
>> 3. semaphore.release() 方法释放许可以供等待的线程获取。释放之后，state 值加一

* AtomicInteger 限制接口请求
> 统计当前正在并发执行的次数。实现方式比较暴力，超过限制的请求直接返回，不能实现削峰

* 令牌桶实现接口请求限制
> 令牌桶算法的原理是系统会以一个恒定的速度往桶里放入令牌，而如果请求需要被处理，则需要先从桶里获取一个令牌，
> 当桶里没有令牌可取时，则拒绝服务。 当桶满时，新添加的令牌被丢弃或拒绝。


## 分布式任务调度系统 - ElasticJob
> 使用 zookeeper 作为注册中心，实现分布式任务
* 分片
> 任务的分布式执行，需要将一个任务拆分为多个独立的任务项，然后由分布式的服务器分别执行某一个或几个分片项。
例如：有一个遍历数据库某张表的作业，现有2台服务器。为了快速的执行作业，那么每台服务器应执行作业的50%。 为满足此需求，可将作业分成2片，每台服务器执行1片。

* 分片规则
> 动态的分配分片项。例如：3台服务器，分成10片，则分片项分配结果为服务器A=0,1,2;服务器B=3,4,5;服务器C=6,7,8,9。 
如果服务器C崩溃，则分片项分配结果为服务器A=0,1,2,3,4;服务器B=5,6,7,8,9。在不丢失分片项的情况下，最大限度的利用现有资源提高吞吐量。

## MySQL 分库分表
* 通过引入 shading 依赖实现 MySQL 读写分离和分库分表
> 1. 读写分离：master 和 slave
> 2. 分库分表：十库百表

## 抢红包
* 悲观锁
> 1. select for update: 线程等待、线程切换、高并发性能低

* 乐观锁
> 1. select * from table where a = a and v = version;
> 2. 客户端请求失败的概率会增大，需要自定义重试次数 3-5 次，对数据库的要求高

* Redis & Lua
> 1. Redis 提供的 lua 脚本支持原子性
> 2. 发红包时将总金额/随机金额列表和总库存缓存到 redis 中，通过 HSET 实现
> 3. 抢红包时首先执行 lua 脚本扣减库存，并从随机金额列表中弹出一个值，作为脚本返回值的一部分
> 4. Java 代码解析返回值以获取库存状态：已抢完、有剩余可抢 和 随机金额
> 5. 扣减 mysql 库存和 用户抢红包记录

## 秒杀
> 1. redis lua 过滤限流：500万放行5000用户请求
> 2. 商品详情通过在后台管理系统添加时，缓存到 redis
> 3. 扣减库存时需要分布式锁：setnx 或者 redission lock 实现

## Linux 线上排查问题

```shell
# 关注 CPU、内存、磁盘、网络

# 查看进程
$ jps
# 查看所有进程 pid 的 CPU 和内存使用情况，定位到指定的进程
$ top

# 将其转换为 16 进制形式 (因为 java native 线程以 16 进制形式输出)
$ printf '%x\n' pid   

# 打印进程栈信息
$ jstack pid

# 查看当前进程 JVM 堆新生代、老年代、持久代等请情况，GC 使用的算法等信息
$ jmap -heap pid

# 输出当前进程内存中所有对象包含的大小
$ jmap -histo:live {pid} | head -n 10 

# 每5秒打印一次GC状况
$ jstat -gc pid 5000

# 查看内存 
$ free -m

# 查看当前目录所占用的磁盘
$ du -d 1 -h 

# 查看磁盘总占用
$ df -ih

# 查看磁盘IO
$ iostat
```

## Linux 常用命令

```shell
 # find
 # 列出当前目录及其子目录中所有一般文件
 $ find . -type f
 
 # 列出当前目录及其子目录中以 .java 的文件
 $ find . -name "*.java"
 
 # 列出当前目录及其子目录下所有最近 20 天内更新过的文件
 $ find . -ctime -20
 
 
 # grep
 # 从文件中读取关键词进行搜索
 $ cat 1.txt | grep -f 2.txt # 输出 1.txt 文件中含有从 2.txt 文件中读取出的关键词的内容行
 
 # 从 1.txt 中读取含 6 的关键词
 $ grep '6' 1.txt # 6、66

 # 多个文件查找关键词 6
 $ grep '6' 2.txt 1.txt
 
 # ^ 关键词开头匹配符
 $ cat 2.txt | grep ^6
 
 # 以 i 为结尾的关键词
 $ cat 1.txt | grep i$  # hi
 
 # java 关键词
 $ ps -ef | grep java
 
 
 # awk: 抽取每行的信息。awk '{pattern + action}' {filenames}
 # $0:所有列内容；$1:第一列内容；默认空格作为分隔符
 # 打印文件每一行第一列内容
 $ cat 1.txt | awk '{print $1}' 
 $ awk '{print $1}' 1.txt
 
 # 以 : 为分隔符，打印每行第一列内容
 $ awk -F ':' '{print $1}' 1.txt
 
 # 打印 2~6 行内容
 # NR：number of records(行记录数)； NF：number of fields(每行字段数)
 $ awk '{if (NR >= 2 && NR <= 6) print $1}' 1.txt
 
 # 统计日志中 INFO 和 ERROR 级别总数
 $ awk '{ 
 if ($3 == "INFO") {info_count++} 
 if ($3 == "ERROR") {error_count++}
 } 
 END {
  print NR;
  print info_count;
  print error_count
} ' test.log

# 打印含 content 的所有行记录
$ awk '/content/ {print}' test.log # 类似于 grep 'destroyed' test.log
```

## disruptor

## RedLock
> 1. Redis 实现分布式锁。可以通过 lua 脚本的 setnx 和 expire 或者在代码中一步实现
> 2. 但是，在发生 failover 即故障转移时，可能存在 master 未及时释放锁，但是之前的
>    slave 升级为 master 并处理获取分布式锁请求，此时会导致不同实例同时获取到了锁
> 3. RedLock 用以解决上述所说的 Redis 分布式锁问题
> 4. RedLock 需要多个独立、无主从关系的 master 实例，客户端需要依次向它们发起获取锁的请求
> 5. 如果在有效时间内有过半实例响应获取锁成功，则客户端分布式锁获取成功
> 6. 释放锁时，所有实例也依次释放锁

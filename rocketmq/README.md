# RocketMQ

## 核心概念
* nameServer：MQ 路由中心
* producer
* consumer
* message queue：队列。类似于 topic 的分区。类似于 kafka 的 partition?
* broker：即 MQ 服务器。kafka 和 rabbitMQ 都有
* topic：主题。kafka 和 rabbitMQ 都有
* tag or SQL: 过滤消息。通过标签或者 SQL 都可以

## RocketMQ 工作流程

* 首先需要先启动 NameServer，接着启动 Broker

* Broker 启动之后就会跟 NameServer 建立**长连接**，每隔一段时间发送心跳包过去，心跳包里需要包含自己当前存储的数据信息，让 NameServer 感知到各个 Broker 的情况

* 创建 Topic，创建 Topic 的时候就会决定这个 Topic 的数据会放在哪些Broker上。而NameServer就保存了整个集群的元数据，知道每个Topic当前数据保存在哪些Broker上

* Producer：在生产消息到Topic的时候，先得找NameServer问一下Topic存放在哪些Broker上，然后就可以跟那些Broker建立连接发送消息过去了。

  Consumer：要从Topic消费消息，也会找NameServer问一下可以从哪些Broker上消费消息，接着同样会跟Broker建立连接并且开始消费消息，

## NameServer 原理分析

*  NameServer 主要的责任就是管理 Broker 的心跳，如果 broker 一段时间内没心跳，那么就认为那个 Broker 已经宕机了，此时要触发对应的Slave切换为Master保证高可用。
* 此外，NameServer 还要负责管理和维护集群元数据，比如有哪些 Topic，每个 Topic 在哪些 Broker 上。
* NameServer 承受的请求压力是很小的，因为心跳和拉取元数据，是很低频的行为，一般部署两三台机器就够了。
* 但是需要注意的是 ，如果集群里维护了几十万个，甚至几百万个Topic，会导致Broker每次心跳上报Topic数据时，带上几十万个Topic的数据信息，可能多达几十MB，那会导致心跳时网络负载过重。

## Broker 原理分析

* Broker主要就是两点：负责接收Producer写入的消息，同时提供消息给Consumer来读取
* 写入消息时，Broker 可以实现高并发高性能的写入

> 1. 主要采用对磁盘文件进行顺序写的方式，磁盘顺序写的性能是很高的，几乎跟内存随机读写的性能差不多
> 2. 就是优先把数据写入os page cahce，也就是操作系统管理的缓存中

* 读取消息时，Broker 也是主要基于os page cahce读取消息，也就是优先从操作系统管理的内存缓存中读取数据，提供给Consumer来消费。因为是优先读内存，所以同样性能也是很高的

## Consumer 原理分析

* 如果部署了Consumer 在多台机器上，默认会把 Topic 下的多个队列的数据均匀分配给各个 Consumer 实例

## 三种发送消息的方式

* 同步
* 异步
* 单向

## 批处理消息
* 通过 send(List) 实现多条消息发送，但是有大小限制

## 延迟消息
* producer 出来的消息在延迟一定时间后，才被 consumer

## 顺序消息

> 在MQ的模型中，顺序需要由3个阶段去保障：
>
> 1. 消息被发送时保持顺序: 通过消息 ID 等轮询发送到到指定的队列(通过IDhash计算)上
> 2. 消息被存储时保持和发送的顺序一致：发送时通过 MessageQueueSelector 实现
> 3. 消息被消费时保持和存储的顺序一致：消费时通过 MessageListenerOrderly 实现

* RocketMQ 通过自己的方式解决了消息顺序性的问题：
RocketMQ通过轮询所有队列的方式来确定消息被发送到哪一个队列（负载均衡策略）
* 根据不同业务，可以将业务ID作为计算队列，让业务ID相同的消息先后发送到同一个队列中，在获取到路由信息以后，会根据 MessageQueueSelector 实现的算法来选择一个队列，同一个 OrderId 获取到的肯定是同一个队列

## 顺序消费者和顺序生产者

* 生产者通过队列选择器实现：MessageQueueSelector
* 消费者通过注册 MessageListenerOrderly 监听器

## 消费者
* 广播模式和集群模式
* 默认为集群模式

## 事务
* 基于两阶段提交实现 2PC：先提交半消息，待确认后再提交本地事务状态，最后由 broker 处理事务
* producer.setTransactionListener()
* producer 执行本地事务状态
* broker 定时查询本地事务状态
* ![rocketmq事务](/Users/lirui/Desktop/rocketmq事务.png)


## 消息消费重试

## 死信队列
* 消息未被成功消费，即使使用了重试。会将消息放到死信队列中
* 不再会被消费者消费，且在一定时间后，自动删除
* 通过后台管理界面操作或者代码中让消费者重新消费

## 消息重复

### 发送时消息重复

- 当一条消息已被成功发送到服务端并完成持久化，此时出现了**网络闪断或者客户端宕机**，导致**服务端对客户端应答失败**。 如果此时生产者意识到消息发送失败并尝试再次发送消息，**消费者后续会收到两条内容相同并且 Message ID 也相同的消息**

### 负载均衡时消息重复

- 当消息队列 RocketMQ 的 Broker 客户端重启、扩容或缩容时，会触发 Rebalance，此时消费者可能会收到重复消息

## 消息幂等性
* 以业务的唯一Id作为幂等处理的依据
* 通过设置生产者 message.setKey("ID");
* 消费时通过 message.getKey("ID) 进行幂等处理

## 参考

1. https://dbaplus.cn/news-73-1123-1.html (消息顺序和重复)
# RocketMQ

## 核心概念
* nameServer：MQ 路由中心
* producer
* consumer
* message queue：队列。类似于 topic 的分区。类似于 kafka 的 partition?
* broker：即 MQ 服务器。kafka 和 rabbitMQ 都有
* topic：主题。kafka 和 rabbitMQ 都有
* tag or SQL: 过滤消息。通过标签或者 SQL 都可以

## 三种发送消息的方式
* 同步
* 异步
* 单向

## 批处理消息
* 通过 send(List) 实现多条消息发送，但是有大小限制

## 延迟消息
* producer 出来的消息在延迟一定时间后，才被 consumer

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


## 消息消费重试

## 死信队列
* 消息未被成功消费，即使使用了重试。会将消息放到死信队列中
* 不再会被消费者消费，且在一定时间后，自动删除
* 通过后台管理界面操作或者代码中让消费者重新消费

## 消息幂等性
* 以业务的唯一Id作为幂等处理的依据
* 通过设置生产者 message.setKey("ID");
* 消费时通过 message.getKey("ID) 进行幂等处理

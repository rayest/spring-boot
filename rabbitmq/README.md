# RabbitMQ
> MacOS 下 通过 homebrew 安装.
> brew install rabbitmq
> http://127.0.0.1:15672/#/ 查看 rabbitMQ 信息. 默认的登录账号 guest/guest

## MQ 的通用目的
> 解耦、削峰、异步

## 核心概念
> producer --> exchanger --> queue --> consumer
> 交换器和队列根据路由键绑定, 组成 broker

* producer
* consumer
* exchanger
* binding
* routing-key: 交换机和队列之间通过路由键绑定
* vhost: 虚拟主机。mini 版本的服务器。命名空间隔离，互补干扰。相当于数据库的 db
* broker: 消息队列实体
* connection --> channel: 连接 --> 信道

## exchanger：交换器
* direct
> 默认的交换器。发布和订阅完全匹配
> 处理路由键。
> 生产者将消息发送到交换器上，并声明路由键。不需要声明队列
> 消费者通过该路由键绑定交换机和队列。实现不同的消费者消费绑定到队列上的消息

* fanout
> 不处理路由键
> producer --> queue --> consumer

* topic
> topic 和 direct 的不同在于，消费者绑定队列和交换机 exchange 与生产者申请的交换机 exchange
> 是通过 */# 等匹配的模式过滤的

## 简单队列
* 一个消费者和生产者对应，不能实现多个消费者消费同一个生产者
* 导致性能不高，也可能产生消息积压
* 引入 work queues：多个消费者消费同一个生产者消息

## work queues
* 多个消费者消费同一个队列消息，不重复消费消息
* 轮询实现选择消费者进行消费

## MQ 自动确认与手动确认
* MQ 默认为自动确认，用户可以设置为手动确认：消费完成后，向 MQ 返回已消费确认
* MQ 在将消息发送给消费者后，将从内存中删除该消息

## 消息持久化
* 通过在声明队列时，设置 durable = true，可以实现持久化

## 订阅
* 一个生产者的消息可以被多个消费者消费
* 生产者将消息发送到交换器上，而不是队列上
* 交换器 exchanger 和队列 queue 通过路由键进行绑定
* 所以：
* producer --> exchanger --> queue1 --> consumer1
* producer --> exchanger --> queue2 --> consumer2
* 不同的消费者消费相同的消息，进行自己的业务处理

## 消息确认机制-事务
* 在发送消息时，将消息发送放在事务中执行
* 引入事务机制，降低了吞吐量

## 消息确认机制-confirm
* 通过 confirm 机制以确认消息是否发送成功

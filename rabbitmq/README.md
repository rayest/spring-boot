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


* fanout

* topic

## 简单队列
* 一个消费者和生产者对应，不能实现多个消费者消费同一个生产者
* 导致性能不高，也可能产生消息积压
* 引入 work queues：多个消费者消费同一个生产者消息

## work queues
* 多个消费者消费同一个队列消息，不重复消费消息
* 轮询实现选择消费者进行消费

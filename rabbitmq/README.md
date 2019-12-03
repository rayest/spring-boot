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

## exchanger：交换器
* direct
> 默认的交换器。发布和订阅完全匹配


* fanout

* topic

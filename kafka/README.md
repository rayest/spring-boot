# kafka

## 基本概念
* zookeeper
> broker 依赖于ZK，zookeeper 在kafka中还用来选举controller 和检测broker是否存活等等。 

* broker
> Kafka server，用来存储消息，Kafka集群中的每一个服务器都是一个Broker，
> 消费者将从broker拉取订阅的消息

* topic
> 消息的主题、队列，每一个消息都有它的topic，Kafka通过topic对消息进行归类。
> Kafka中可以将Topic从物理上划分成一个或多个分区（Partition），
> 每个分区在物理上对应一个文件夹，以”topicName_partitionIndex”的命名方式命名，
> 该dir包含了这个分区的所有消息(.log)和索引文件(.index)，这使得Kafka的吞吐率可以水平扩展。

* partition
> 每个分区都是一个顺序的、不可变的消息队列， 并且可以持续的添加;
> 分区中的消息都被分了一个序列号，称之为偏移量(offset)，在每个分区中此偏移量都是唯一的。
> producer在发布消息的时候，可以为每条消息指定Key，这样消息被发送到broker时，
> 会根据分区算法把消息存储到对应的分区中（一个分区存储多个消息），如果分区规则设置的合理，
> 那么所有的消息将会被均匀的分布到不同的分区中，这样就实现了负载均衡。

* consumer group
> 同一个Consumer Group中的Consumers，Kafka将相应Topic中的每个消息只发送给其中一个Consumer

* kafka 为什么那么快
> Cache Filesystem Cache PageCache 缓存
> Zero-copy 零拷技术减少拷贝次数 
> Batching of Messages 批量量处理。合并小的请求，然后以流的方式进行交互，直顶网络上限。
> Pull 拉模式 使用拉模式进行消息的获取消费，与消费端处理能力相符

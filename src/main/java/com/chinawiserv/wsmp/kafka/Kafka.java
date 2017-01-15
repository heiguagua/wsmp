package com.chinawiserv.wsmp.kafka;

import java.util.Properties;

// kafka-topics.sh --zookeeper slave1.dom:2181,slave2.dom:2181,slave3.dom:2181 --create --topic dom --replication-factor 1 --partitions 9
// kafka-topics.sh --zookeeper slave1.dom:2181,slave2.dom:2181,slave3.dom:2181 --delete --topic dom
// kafka-topics.sh --zookeeper slave1.dom:2181,slave2.dom:2181,slave3.dom:2181 --list
// kafka-topics.sh --zookeeper slave1.dom:2181,slave2.dom:2181,slave3.dom:2181 --describe dom
// kafka-console-consumer.sh --zookeeper slave1.dom:2181,slave2.dom:2181,slave3.dom:2181 --topic dom --from-beginning

public class Kafka {

    protected Properties getPropertiesForWriter(String brokers) {
        Properties props = new Properties();
        //用于建立与kafka集群连接的host/port组。
        // 数据将会在所有servers上均衡加载，不管哪些server是指定用于bootstrapping。
        props.put("bootstrap.servers", brokers);

        //producer需要server接收到数据之后发出的确认接收的信号，此项配置就是指procuder需要多少个这样的确认信号。
        //此配置实际上代表了数据备份的可用性。以下设置为常用选项：
        //（1）acks=0： 设置为0表示producer不需要等待任何确认收到的信息。副本将立即加到socket buffer并认为已经发送。没有任何保障可以保证此种情况下server已经成功接收数据，同时重试配置不会发生作用（因为客户端不知道是否失败）回馈的offset会总是设置为-1；
        //（2）acks=1： 这意味着至少要等待leader已经成功将数据写入本地log，但是并没有等待所有follower是否成功写入。这种情况下，如果follower没有成功备份数据，而此时leader又挂掉，则消息会丢失。
        //（3）acks=all： 这意味着leader需要等待所有备份都成功写入日志，这种策略会保证只要有一个备份存活就不会丢失数据。这是最强的保证。
        props.put("acks", "all");

        //设置大于0的值将使客户端重新发送任何数据，一旦这些数据发送失败。
        //注意，这些重试与客户端接收到发送错误时的重试没有什么不同。
        //允许重试将潜在的改变数据的顺序，如果这两个消息记录都是发送到同一个partition，
        //则第一个消息失败第二个发送成功，则第二条消息会比第一条消息出现要早。
        props.put("retries", "0");

        //producer将试图批处理消息记录，以减少请求次数。
        //这将改善client与server之间的性能。
        //这项配置控制默认的批量处理消息字节数。
        //不会试图处理大于这个字节数的消息字节数。
        //发送到brokers的请求将包含多个批量处理，其中会包含对每个partition的一个请求。
        //较小的批量处理数值比较少用，并且可能降低吞吐量（0则会仅用批量处理）。
        //较大的批量处理数值将会浪费更多内存空间，这样就需要分配特定批量处理数值的内存大小。
        props.put("batch.size", "16384");

        //producer组将会汇总任何在请求与发送之间到达的消息记录一个单独批量的请求。
        //通常来说，这只有在记录产生速度大于发送速度的时候才能发生。
        //然而，在某些条件下，客户端将希望降低请求的数量，甚至降低到中等负载一下。
        //这项设置将通过增加小的延迟来完成–即，不是立即发送一条记录，
        //producer将会等待给定的延迟时间以允许其他消息记录发送，这些消息记录可以批量处理。
        //这可以认为是TCP种Nagle的算法类似。这项设置设定了批量处理的更高的延迟边界：
        //一旦我们获得某个partition的batch.size，他将会立即发送而不顾这项设置，
        //然而如果我们获得消息字节数比这项设置要小的多，我们需要“linger”特定的时间以获取更多的消息。
        //这个设置默认为0，即没有延迟。设定linger.ms=5，例如，将会减少请求数目，但是同时会增加5ms的延迟。
        props.put("linger.ms", "1");

        //producer可以用来缓存数据的内存大小。
        //如果数据产生速度大于向broker发送的速度，producer会阻塞或者抛出异常，
        //以“block.on.buffer.full”来表明。这项设置将和producer能够使用的总内存相关，
        //但并不是一个硬性的限制，因为不是producer使用的所有内存都是用于缓存。
        //一些额外的内存会用于压缩（如果引入压缩机制），同样还有一些用于维护请求。
        props.put("buffer.memory", "33554432");

        //producer用于压缩数据的压缩类型。
        // 默认是无压缩。
        // 正确的选项值是none、gzip、snappy。压缩最好用于批量处理，批量处理消息越多，压缩性能越好
        props.put("compression.type", "snappy");

        //key的序列化方式，若是没有设置，同serializer.class
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        //value序列化类方式
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        //当向server发出请求时，这个字符串会发送给server。
        //目的是能够追踪请求源头，以此来允许ip/port许可列表之外的一些应用可以发送信息。
        //这项应用可以设置任意字符串，因为没有任何功能性的目的，除了记录和跟踪。
        props.put("client.id", "DeepOne-DDB-Kafka-Writer");

        //group.id
        props.put("group.id", "DeepOneDDBWriter");

        return props;
    }


    protected Properties getPropertiesForReader(String brokers) {
        Properties props = new Properties();
        //用于建立与kafka集群连接的host/port组。
        // 数据将会在所有servers上均衡加载，不管哪些server是指定用于bootstrapping。
        props.put("bootstrap.servers", brokers);

        //producer需要server接收到数据之后发出的确认接收的信号，此项配置就是指procuder需要多少个这样的确认信号。
        //此配置实际上代表了数据备份的可用性。以下设置为常用选项：
        //（1）acks=0： 设置为0表示producer不需要等待任何确认收到的信息。副本将立即加到socket buffer并认为已经发送。没有任何保障可以保证此种情况下server已经成功接收数据，同时重试配置不会发生作用（因为客户端不知道是否失败）回馈的offset会总是设置为-1；
        //（2）acks=1： 这意味着至少要等待leader已经成功将数据写入本地log，但是并没有等待所有follower是否成功写入。这种情况下，如果follower没有成功备份数据，而此时leader又挂掉，则消息会丢失。
        //（3）acks=all： 这意味着leader需要等待所有备份都成功写入日志，这种策略会保证只要有一个备份存活就不会丢失数据。这是最强的保证。
        props.put("acks", "all");

        //设置大于0的值将使客户端重新发送任何数据，一旦这些数据发送失败。
        //注意，这些重试与客户端接收到发送错误时的重试没有什么不同。
        //允许重试将潜在的改变数据的顺序，如果这两个消息记录都是发送到同一个partition，
        //则第一个消息失败第二个发送成功，则第二条消息会比第一条消息出现要早。
        props.put("retries", "0");

        //producer将试图批处理消息记录，以减少请求次数。
        //这将改善client与server之间的性能。
        //这项配置控制默认的批量处理消息字节数。
        //不会试图处理大于这个字节数的消息字节数。
        //发送到brokers的请求将包含多个批量处理，其中会包含对每个partition的一个请求。
        //较小的批量处理数值比较少用，并且可能降低吞吐量（0则会仅用批量处理）。
        //较大的批量处理数值将会浪费更多内存空间，这样就需要分配特定批量处理数值的内存大小。
        props.put("batch.size", "16384");

        //producer组将会汇总任何在请求与发送之间到达的消息记录一个单独批量的请求。
        //通常来说，这只有在记录产生速度大于发送速度的时候才能发生。
        //然而，在某些条件下，客户端将希望降低请求的数量，甚至降低到中等负载一下。
        //这项设置将通过增加小的延迟来完成–即，不是立即发送一条记录，
        //producer将会等待给定的延迟时间以允许其他消息记录发送，这些消息记录可以批量处理。
        //这可以认为是TCP种Nagle的算法类似。这项设置设定了批量处理的更高的延迟边界：
        //一旦我们获得某个partition的batch.size，他将会立即发送而不顾这项设置，
        //然而如果我们获得消息字节数比这项设置要小的多，我们需要“linger”特定的时间以获取更多的消息。
        //这个设置默认为0，即没有延迟。设定linger.ms=5，例如，将会减少请求数目，但是同时会增加5ms的延迟。
        props.put("linger.ms", "1");

        //producer可以用来缓存数据的内存大小。
        //如果数据产生速度大于向broker发送的速度，producer会阻塞或者抛出异常，
        //以“block.on.buffer.full”来表明。这项设置将和producer能够使用的总内存相关，
        //但并不是一个硬性的限制，因为不是producer使用的所有内存都是用于缓存。
        //一些额外的内存会用于压缩（如果引入压缩机制），同样还有一些用于维护请求。
        props.put("buffer.memory", "33554432");

        //producer用于压缩数据的压缩类型。
        // 默认是无压缩。
        // 正确的选项值是none、gzip、snappy。压缩最好用于批量处理，批量处理消息越多，压缩性能越好
        props.put("compression.type", "snappy");

        //key的序列化方式，若是没有设置，同serializer.class
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        //value序列化类方式
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        //当向server发出请求时，这个字符串会发送给server。
        //目的是能够追踪请求源头，以此来允许ip/port许可列表之外的一些应用可以发送信息。
        //这项应用可以设置任意字符串，因为没有任何功能性的目的，除了记录和跟踪。
        props.put("client.id", "DeepOne-DDB-Kafka-Reader");

        //group.id
        props.put("group.id", "DeepOneDDBReader");

        return props;
    }

}

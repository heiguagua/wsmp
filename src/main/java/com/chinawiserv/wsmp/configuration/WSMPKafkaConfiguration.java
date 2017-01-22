package com.chinawiserv.wsmp.configuration;

import com.chinawiserv.model.Cmd;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.AsyncListenableTaskExecutor;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.AbstractMessageListenerContainer;
import org.springframework.kafka.listener.config.ContainerProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chinawiserv-0006 kafka 配置类
 */
@Configuration
@EnableKafka
public class WSMPKafkaConfiguration {

    @Bean
    public ConsumerFactory<String, Cmd> consumerFactory(@Value("#{consumerConfig}") Map<String, Object> configs) {
        return new DefaultKafkaConsumerFactory<>( configs );
    }

    @Bean("kafkaListenerContainerFactory")
    <K, V> KafkaListenerContainerFactory<WSMPConcurrentMessageListenerContainer<K, V>> containerFactory(
            @Value("${kafka.consumer.count}") int concurrency,
            @Value("${kafka.consumer.queueDepth}") int queueDepth,
            @Value("${kafka.consumer.pausemilliseconds}") long pausemilliseconds,
            AsyncListenableTaskExecutor listenerTaskExecutor,
            ConsumerFactory<K, V> consumerFactory) {

        final WSMPConcurrentKafkaListenerContainerFactory<K, V> factory = new WSMPConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory( consumerFactory );
        factory.setConcurrency( concurrency );

        final ContainerProperties containerProperties = factory.getContainerProperties();

        containerProperties.setListenerTaskExecutor(listenerTaskExecutor);
        containerProperties.setQueueDepth(queueDepth);
        containerProperties.setPauseAfter(pausemilliseconds);
        containerProperties.setPollTimeout( Long.MAX_VALUE );
        containerProperties.setAckMode( AbstractMessageListenerContainer.AckMode.BATCH );
        containerProperties.setPauseEnabled(true);
        return factory;
    }

    @Bean("consumerConfig")
    public Map<String, Object> consumerConfigs(
            @Value("${kafka.consumer.bootstrap.servers}") String servers,
            @Value("${kafka.consumer.enable.auto.commit}") boolean autoComit,
            @Value("${kafka.consumer.group.id}") String groupId,
            @Value("${kafka.consumer.receive.buffer.bytes}") String buffSize,
            @Value("${kafka.consumer.max.partition.fetch.bytes}") String fetchSize,
            @Value("${kafka.value.deserializer}") Class<?> valueClazz
    ) {

        final Map<String, Object> propsMap = new HashMap<>();

        propsMap.put( ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, servers );
        propsMap.put( ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, autoComit );
        propsMap.put( ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, 1200 );
        propsMap.put( ConsumerConfig.RECEIVE_BUFFER_CONFIG, buffSize );
        propsMap.put( ConsumerConfig.MAX_PARTITION_FETCH_BYTES_CONFIG, fetchSize );
        propsMap.put( ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, "20000" );
        propsMap.put( ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "21000" );
        propsMap.put( ConsumerConfig.REQUEST_TIMEOUT_MS_CONFIG, "32000" );
        propsMap.put( ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class );
        propsMap.put( ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, valueClazz );
        propsMap.put( ConsumerConfig.GROUP_ID_CONFIG, groupId );
        //latest  earliest
        propsMap.put( ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest" );

        return propsMap;
    }

}

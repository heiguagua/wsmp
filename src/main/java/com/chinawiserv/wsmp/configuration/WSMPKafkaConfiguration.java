package com.chinawiserv.wsmp.configuration;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chinawiserv-0006 kafka 配置类
 */
@Configuration
@EnableKafka
public class WSMPKafkaConfiguration {
	
	
	@Bean("kafkaListenerContainerFactory")
	KafkaListenerContainerFactory<WSMPConcurrentMessageListenerContainer<String, String>> containerFactory(
			ConsumerFactory<String, String> consumerFactory) {
		
		final WSMPConcurrentKafkaListenerContainerFactory<String, String> factory = new WSMPConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory);
		factory.setConcurrency(1);
		factory.getContainerProperties().setPollTimeout(3000);
		return factory;
	}
	
	@Bean
	public ConsumerFactory<String, String> consumerFactory(@Value("#{consumerConfig}") Map<String, Object> configs) {
		return new DefaultKafkaConsumerFactory<>(configs);
	}

	@Bean("consumerConfig")
	public Map<String, Object> consumerConfigs(@Value("${kafka.bootstrap.servers}") String servers) {
		final Map<String, Object> propsMap = new HashMap<>();

		propsMap.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, servers);
		propsMap.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
		propsMap.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "100");
		propsMap.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "15000");
		propsMap.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		propsMap.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		propsMap.put(ConsumerConfig.GROUP_ID_CONFIG, "wsmp1");
		propsMap.put(ConsumerConfig.CLIENT_ID_CONFIG, "wsmp_radio");
		propsMap.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

		return propsMap;
	}

}

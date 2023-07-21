package com.example.kafka.config;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.common.config.TopicConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaTopicConfiguration {

	public static final String WIKI_TOPIC = "wiki-changes";

	@Value(value = "${spring.kafka.bootstrap-servers}")
	private String bootstrapAddress;

	@Bean
	public KafkaAdmin kafkaAdmin() {
		Map<String, Object> configs = new HashMap<>();
		configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
		return new KafkaAdmin(configs);
	}

	@Bean
	public NewTopic topicStr() {
		Map<String, String> configs = new HashMap<>();
		configs.put(TopicConfig.MIN_IN_SYNC_REPLICAS_CONFIG, "2"); // HOW MANY REPLICAS SHOULD RETURN ACKS WHEN ACKS_CONFIG=all, SHOULD BE >= 1 AND < RF,
		return TopicBuilder											  // FOR ANY RF >= 3, 2 IS A GOOD VALUE, BECAUSE YOU CHECKING FOR AT LEAST ONE REPLICA TO BE AVAILABLE
				.name(KafkaTopicConfiguration.WIKI_TOPIC)
				.partitions(5)
				.replicas(3)                               // REPLICATION FACTOR = 3, SHOULD NOT BE GREATER THAN PARTITIONS NUMBER
				.configs(configs)
				.build();
	}
}

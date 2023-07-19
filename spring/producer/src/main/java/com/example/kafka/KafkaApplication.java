package com.example.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootApplication
public class KafkaApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkaApplication.class, args);
	}

	@Bean
	public NewTopic topicStr() {
		return TopicBuilder.name("spring-kafka-str")
				.partitions(5)
				.replicas(2)
				.build();
	}

	@Bean
	public NewTopic topicObj() {
		return TopicBuilder.name("spring-kafka-obj")
				.partitions(5)
				.replicas(2)
				.build();
	}
}

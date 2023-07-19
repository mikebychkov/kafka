package com.example.kafka;

import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.*;

@SpringBootApplication
@Log4j2
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

	@KafkaListener(groupId = "consumer-group-1", topics = "spring-kafka-str")
	public void listen(@Payload String in,
					   @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key,
					   @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition,
					   @Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
					   @Header(KafkaHeaders.RECEIVED_TIMESTAMP) long ts) {
		log.info(in);
		log.info(key);
		log.info(partition);
		log.info(topic);
		log.info(ts);
	}

	@KafkaListener(groupId = "consumer-group-1", topics = "spring-kafka-obj", containerFactory = "objectKafkaListenerContainerFactory")
	public void handleObject(@Payload MyMessage in) {
		log.info(in);
	}
}

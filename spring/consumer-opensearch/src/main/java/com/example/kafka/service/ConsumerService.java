package com.example.kafka.service;

import com.example.kafka.dto.MyMessage;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class ConsumerService {

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

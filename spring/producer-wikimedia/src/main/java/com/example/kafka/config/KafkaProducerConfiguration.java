package com.example.kafka.config;

import com.example.kafka.dto.WikiChange;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaProducerConfiguration {

    private final Map<String, Object> configProps = new HashMap<>();

    public KafkaProducerConfiguration(@Value("${spring.kafka.bootstrap-servers}") String bootstrapServers) {
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

//        DELIVERY SEMANTICS:

        configProps.put(ProducerConfig.ACKS_CONFIG, "all");                 // "all" - leader + replica; 1 - leader // 'AT LEAST ONCE'
        configProps.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);    // 'EXACT ONCE' AND 'AT LEAST ONCE'
    }

    @Bean
    public ProducerFactory<String, WikiChange> objectProducerFactory() {
//        configProps.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, "ObjectTransactionsId"); // 'EXACT ONCE'
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public ProducerFactory<String, String> stringProducerFactory() {
//        configProps.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, "StringTransactionsId"); // 'EXACT ONCE'
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, WikiChange> objectKafkaTemplate() {
        return new KafkaTemplate<>(objectProducerFactory());
    }

    @Bean
    public KafkaTemplate<String, String> stringKafkaTemplate() {
        return new KafkaTemplate<>(stringProducerFactory());
    }
}

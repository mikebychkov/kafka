package com.example.kafka.config;

import com.example.kafka.dto.WikiChange;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.record.CompressionType;
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

//        RETRY OPTIONS:

//        configProps.put(ProducerConfig.RETRIES_CONFIG, ); // DEFAULT INT MAX FOR > v2.1
//        configProps.put(ProducerConfig.RETRY_BACKOFF_MS_CONFIG, 100); // DEFAULT 100ms, HOW MANY TIME TO WAIT UNTIL NEXT TRY

//        COMBINED RETRY TIMEOUT OPTION:
//        configProps.put(ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG, 120000); // DEFAULT 2min, OPTION SINCE v2.1, >= linger.ms + retry.backoff.ms + request.timeout.ms

        configProps.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, 5); // VALUE OF 5 - HIGH PERFORMANCE AND ORDERING WHILE RETRIES

//        MSG COMPRESSION:
        configProps.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "lz4"); // GZIP - MAX COMPRESSION; LZ4 - OPTIMAL PERFORMANCE

//        BATCHING:
        configProps.put(ProducerConfig.BATCH_SIZE_CONFIG, 32*1024); // DEFAULT 16 (KB) WITH BIGGER BATCH SIZE EFFICIENCY OF COMPRESSION IS GROWING
        configProps.put(ProducerConfig.LINGER_MS_CONFIG, 20); // (MS) HOW MANY TIME TO WAIT UNTIL SEND BATCH
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

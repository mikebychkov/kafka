package com.example.kafka;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.OffsetResetStrategy;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;
@Configuration
public class MyConfig {
    private final Map<String, Object> configProps = new HashMap<>();
    public MyConfig(@Value("${spring.kafka.bootstrap-servers}") String bootstrapServers) {
        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");                     // DEFAULT VALUE 'latest' ('earliest')
        configProps.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);                       // DEFAULT VALUE 'false', true - 'AT MOST ONCE', false - 'AT LEAST ONCE'
//        configProps.put(ConsumerConfig.ISOLATION_LEVEL_CONFIG, IsolationLevel.READ_COMMITTED);   // 'EXACT ONCE'
    }
    @Bean
    public ConsumerFactory<String, MyMessage> objectConsumerFactory() {
        configProps.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaConsumerFactory<>(
                configProps,
                new StringDeserializer(),
                new JsonDeserializer<>(MyMessage.class));
    }
    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, MyMessage> objectKafkaListenerContainerFactory() {

        ConcurrentKafkaListenerContainerFactory<String, MyMessage> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(objectConsumerFactory());
//        factory.setConcurrency(3); // IF THERE IS MULTIPLE CONSUMERS THAN ContainerFactory.concurrency PROBABLY SHOULD BE 1 (default), ELSE INCREASING THIS NUMBER MAKE SENSE
        return factory;              // CAN BE SET THROUGH ANNOTATION
    }
}

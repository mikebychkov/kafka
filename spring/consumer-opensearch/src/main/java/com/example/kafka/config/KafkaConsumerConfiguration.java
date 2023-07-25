package com.example.kafka.config;

import com.example.kafka.dto.MyMessage;
import org.apache.kafka.clients.consumer.ConsumerConfig;
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
public class KafkaConsumerConfiguration {
    private final Map<String, Object> configProps = new HashMap<>();
    public KafkaConsumerConfiguration(@Value("${spring.kafka.bootstrap-servers}") String bootstrapServers) {
        configProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        configProps.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringSerializer.class);

//        DELIVERY SEMANTICS

        configProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");                     // DEFAULT VALUE 'latest' ('earliest')
        configProps.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);                       // DEFAULT VALUE 'false', true - 'AT MOST ONCE', false - 'AT LEAST ONCE'
//        configProps.put(ConsumerConfig.ISOLATION_LEVEL_CONFIG, IsolationLevel.READ_COMMITTED);   // 'EXACT ONCE'

//        RE-BALANCE CONSUMERS:

//        configProps.put(ConsumerConfig.PARTITION_ASSIGNMENT_STRATEGY_CONFIG, CooperativeStickyAssignor.class.getName()); // CooperativeStickyAssignor IS THE BEST CHOICE, BECAUSE SUPPORTS INCREMENTAL RE-BALANCE

//        configProps.put(ConsumerConfig.GROUP_INSTANCE_ID_CONFIG, "StaticConsumerForObjectTopic"); // SETTING THIS MAKES CONSUMER STATIC GROUP MEMBER
//        configProps.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, 2000);                          // IF STATIC CONSUMER OUT AND BACK INSIDE SESSION TIMEOUT
//                                                                                                     // WINDOW THEN CONSUMER RE-BALANCE NOT STARTED AND PARTITION
//                                                                                                     // NOT ASSIGNED TO ANOTHER CONSUMER
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

package com.example.kafka.service;

import com.example.kafka.config.KafkaTopicConfiguration;
import com.example.kafka.dto.WikiChange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.Optional;

@Service
public class ProducerServiceImpl implements ProducerService {

    @Autowired
    @Qualifier("objectKafkaTemplate")
    private KafkaTemplate<String, WikiChange> kafkaTemplateObject;

    @Override
    public ListenableFuture<SendResult<String, WikiChange>> send(WikiChange change) {

        String id = Optional.ofNullable(change.getId())
                .map(l -> Long.toString(l))
                .orElse("null");

        ListenableFuture<SendResult<String, WikiChange>> result =
                kafkaTemplateObject.send(KafkaTopicConfiguration.WIKI_TOPIC, id, change);

        return result;
    }
}

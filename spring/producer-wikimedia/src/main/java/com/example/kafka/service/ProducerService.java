package com.example.kafka.service;

import com.example.kafka.dto.WikiChange;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;

public interface ProducerService {

    ListenableFuture<SendResult<String, WikiChange>> send(WikiChange change);
}

package com.example.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/msg")
public class KafkaController {

    @Autowired
    @Qualifier("stringKafkaTemplate")
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    @Qualifier("objectKafkaTemplate")
    private KafkaTemplate<String, MyMessage> kafkaTemplateObject;

    @PostMapping
    public ResponseEntity<?> postMessage(@RequestBody MyMessage body) {

        ListenableFuture<SendResult<String, String>> result =
                kafkaTemplate.send("spring-kafka-str", body.getKey(), body.getMsg());

        return ResponseEntity.ok().build();
    }

    @PostMapping("/obj")
    public ResponseEntity<?> postMessageObject(@RequestBody MyMessage body) {

        ListenableFuture<SendResult<String, MyMessage>> result =
                kafkaTemplateObject.send("spring-kafka-obj", body.getKey(), body);

        return ResponseEntity.ok().build();
    }
}

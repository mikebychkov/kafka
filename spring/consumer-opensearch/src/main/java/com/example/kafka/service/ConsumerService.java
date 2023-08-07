package com.example.kafka.service;

import com.example.kafka.config.KafkaTopicConfiguration;
import com.example.kafka.dto.WikiChange;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.opensearch.action.bulk.BulkRequest;
import org.opensearch.action.bulk.BulkResponse;
import org.opensearch.action.index.IndexRequest;
import org.opensearch.action.index.IndexResponse;
import org.opensearch.client.RequestOptions;
import org.opensearch.client.RestHighLevelClient;
import org.opensearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@Log4j2
public class ConsumerService {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RestHighLevelClient openSearchClient;

//    @KafkaListener(groupId = "wiki-consumer-group-1", topics = KafkaTopicConfiguration.WIKI_TOPIC, containerFactory = "objectKafkaListenerContainerFactory")
//    public void handleObject(@Payload WikiChange in) throws IOException {
//
//        log.info(in);
//
//        IndexRequest indexRequest = new IndexRequest("wikimedia")
//                .source(objectMapper.convertValue(in, JsonNode.class).toString(), XContentType.JSON)
//                .id(in.getMeta().getId());
//
//        IndexResponse response = openSearchClient.index(indexRequest, RequestOptions.DEFAULT);
//
//        log.info(response);
//    }

    @KafkaListener(groupId = "wiki-consumer-group-1", topics = KafkaTopicConfiguration.WIKI_TOPIC, containerFactory = "objectKafkaListenerContainerFactory")
    public void handleBatch(@Payload List<WikiChange> in) throws IOException {

        log.info(in);

        BulkRequest bulkRequest = new BulkRequest();

        in.forEach(wc -> {
            IndexRequest indexRequest = new IndexRequest("wikimedia")
                    .source(objectMapper.convertValue(wc, JsonNode.class).toString(), XContentType.JSON)
                    .id(wc.getMeta().getId());

            bulkRequest.add(indexRequest);
        });

        BulkResponse bulkResponse = openSearchClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        log.info("Inserted " + bulkResponse.getItems().length + " record(s).");
    }
}

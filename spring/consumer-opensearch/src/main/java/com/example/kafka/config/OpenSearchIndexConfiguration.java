package com.example.kafka.config;

import lombok.extern.log4j.Log4j2;
import org.opensearch.client.RequestOptions;
import org.opensearch.client.RestHighLevelClient;
import org.opensearch.client.indices.CreateIndexRequest;
import org.opensearch.client.indices.GetIndexRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Component
@Log4j2
public class OpenSearchIndexConfiguration {

    @Autowired
    private RestHighLevelClient openSearchClient;

    @PostConstruct
    void indexInit() throws IOException {

        boolean indexExists = openSearchClient.indices().exists(new GetIndexRequest("wikimedia"), RequestOptions.DEFAULT);

        if (!indexExists){
            CreateIndexRequest createIndexRequest = new CreateIndexRequest("wikimedia");
            openSearchClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);
            log.info("The Wikimedia Index has been created!");
        } else {
            log.info("The Wikimedia Index already exits");
        }
    }
}

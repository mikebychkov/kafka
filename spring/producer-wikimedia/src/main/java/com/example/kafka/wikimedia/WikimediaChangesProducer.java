package com.example.kafka.wikimedia;

import com.example.kafka.dto.WikiChange;
import com.example.kafka.service.ProducerService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@Service
@Log4j2
public class WikimediaChangesProducer {

    @Autowired
    private ProducerService producerService;

    ObjectMapper objectMapper = new ObjectMapper();

    @Scheduled(fixedDelay = 5 * 60 * 1000)
    public void launchDataFetch() throws InterruptedException {

        log.info("STARTING DATA FETCH");

        CompletableFuture<Disposable> future = CompletableFuture.supplyAsync(this::run);
        Thread.sleep(10_000);
        try {
            future.get().dispose();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }

        log.info("END DATA FETCH");
    }

    private Disposable run() {

        var webClient = WebClient.builder().baseUrl("https://stream.wikimedia.org")
                .build();

        Flux<JsonNode> changeFlux = webClient.get()
                .uri("/v2/stream/recentchange")
                .retrieve()
                .bodyToFlux(JsonNode.class);

        return changeFlux.take(Duration.of(10, ChronoUnit.SECONDS))
                .subscribe(ch -> {
                    WikiChange ent = objectMapper.convertValue(ch, WikiChange.class);
                    log.info("CHANGE ENTITY: {}", ent);
                    producerService.send(ent);
                });
    }
}
package com.example.kafka.webflux_examples;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

//@Service
public class WebClientExamples {

    private final WebClient webClient;

    @Autowired
    public WebClientExamples(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8888/api/v1")
                .clientConnector(new ReactorClientHttpConnector(HttpClient.create().wiretap(true)))
                .build();
    }


//    @Override
//    public Flux<BeerDTO> listBeersFlux(int pageNumber, int pageSize, String beerName, BeerStyle beerStyle, Boolean showInventoryOnHand) {
//
//        return webClient.get().uri(uriBuilder ->
//                uriBuilder.path("/beer-flux")
//                        .queryParam("pageNumber", pageNumber)
//                        .queryParam("pageSize", pageSize)
//                        .queryParam("beerName", beerName)
//                        .queryParam("beerStyle", beerStyle)
//                        .queryParam("showInventoryOnHand", showInventoryOnHand)
//                        .build()
//        ).retrieve().bodyToFlux(BeerDTO.class);
//    }
//
//    @Override
//    public Mono<BeerDTO> beerById(String id, Boolean showInventoryOnHand) {
//
//          return webClient.get().uri(
//                uriBuilder -> uriBuilder.path("/beer/{id}")
//                    .queryParamIfPresent("showInventoryOnHand", Optional.ofNullable(showInventoryOnHand))
//                    .build(id)
//          ).retrieve().bodyToMono(BeerDTO.class);
//    }
//
//    @Override
//    public Mono<BeerDTO> beerByUpc(String upc) {
//
//        return webClient.get().uri("/beerUpc/{upc}", upc).retrieve().bodyToMono(BeerDTO.class);
//    }
//
//    @Override
//    public Mono<ResponseEntity<Void>> addBeer(BeerDTO body) {
//
//        //return webClient.post().uri("/beer").bodyValue(body).retrieve().bodyToMono(ResponseEntity.class);
//        return webClient.post().uri("/beer").bodyValue(body).retrieve().toBodilessEntity();
//    }
}

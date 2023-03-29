package com.example.acclientone.controller;

import com.example.acclientone.model.ErrorBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/api/webclient")
@RequiredArgsConstructor
public class WebClientErrorController {

    private final WebClient serviceTwoWebClient;

    @GetMapping("/get400")
    public Mono<ResponseEntity<ErrorBody>> getError400() {
        return serviceTwoWebClient
                .get()
                .uri("/webclient/getError400")
                .retrieve()
                .bodyToMono(ErrorBody.class)
                .map(errorBody -> new ResponseEntity<>(errorBody, HttpStatusCode.valueOf(400)));
    }

    @GetMapping("/get403")
    public ResponseEntity<ErrorBody> getError403() {
        ErrorBody response = serviceTwoWebClient
                .get()
                .uri("/webclient/getError403")
                .retrieve()
                .bodyToMono(ErrorBody.class)
                .block();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/get404")
    public ResponseEntity<ErrorBody> getError404() {
        ErrorBody response = serviceTwoWebClient
                .get()
                .uri("/webclient/getError404")
                .retrieve()
                .bodyToMono(ErrorBody.class)
                .block();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/get409")
    public ResponseEntity<ErrorBody> getError409() {
        ErrorBody response = serviceTwoWebClient
                .get()
                .uri("/webclient/getError409")
                .retrieve()
                .bodyToMono(ErrorBody.class)
                .block();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/get412")
    public ResponseEntity<ErrorBody> getError412() {
        ErrorBody response = serviceTwoWebClient
                .get()
                .uri("/webclient/getError412")
                .retrieve()
                .bodyToMono(ErrorBody.class)
                .block();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/get429")
    public ResponseEntity<ErrorBody> getError429() {
        ErrorBody response = serviceTwoWebClient
                .get()
                .uri("/webclient/getError429")
                .retrieve()
                .bodyToMono(ErrorBody.class)
                .block();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/get433")
    public ResponseEntity<ErrorBody> getError433() {
        ErrorBody response = serviceTwoWebClient
                .get()
                .uri("/webclient/getError433")
                .retrieve()
                .bodyToMono(ErrorBody.class)
                .block();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/get500")
    public ResponseEntity<ErrorBody> getError500() {
        ErrorBody response = serviceTwoWebClient
                .get()
                .uri("/webclient/getError500")
                .retrieve()
                .bodyToMono(ErrorBody.class)
                .block();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/get502")
    public ResponseEntity<ErrorBody> getError502() {
        ErrorBody response = serviceTwoWebClient
                .get()
                .uri("/webclient/getError502")
                .retrieve()
                .bodyToMono(ErrorBody.class)
                .block();

        return ResponseEntity.ok(response);
    }
}


package com.example.acserviceone.controller;

import com.example.acserviceone.model.ErrorBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/webclient")
public class WebclientErrorController {

    @GetMapping("/getError400")
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public Mono<ResponseEntity<ErrorBody>> getError400(){
        return Mono.just(ResponseEntity.badRequest().body(ErrorBody.builder().errorDescription("400 error").errorCode(400).build()));
    }

    @GetMapping("/getError403")
    @ResponseStatus(code = HttpStatus.FORBIDDEN)
    public Mono<ResponseEntity<ErrorBody>> getError403(){
        return Mono.just(new ResponseEntity(ErrorBody.builder().errorDescription("403 error").errorCode(403).build(), HttpStatus.FORBIDDEN));
    }

    @GetMapping("/getError404")
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    public Mono<ResponseEntity<ErrorBody>> getError404(){
        return Mono.just(new ResponseEntity(ErrorBody.builder().errorDescription("404 error").errorCode(404).build(), HttpStatus.NOT_FOUND));
    }

    @GetMapping("/getError409")
    @ResponseStatus(code = HttpStatus.CONFLICT)
    public Mono<ResponseEntity<ErrorBody>> getError409(){
        return Mono.just(new ResponseEntity(ErrorBody.builder().errorDescription("409 error").errorCode(409).build(), HttpStatus.CONFLICT));
    }

    @GetMapping("/getError412")
    @ResponseStatus(code = HttpStatus.PRECONDITION_FAILED)
    public Mono<ResponseEntity<ErrorBody>> getError412(){
        return Mono.just(new ResponseEntity(ErrorBody.builder().errorDescription("412 error").errorCode(412).build(), HttpStatus.PRECONDITION_REQUIRED));
    }

    @GetMapping("/getError429")
    @ResponseStatus(code = HttpStatus.TOO_MANY_REQUESTS)
    public Mono<ResponseEntity<ErrorBody>> getError429(){
        return Mono.just(new ResponseEntity(ErrorBody.builder().errorDescription("429 error").errorCode(429).build(), HttpStatus.TOO_MANY_REQUESTS));
    }

    @GetMapping("/getError433")
    public Mono<ResponseEntity<ErrorBody>> getError433(){
        return Mono.just(new ResponseEntity(ErrorBody.builder().errorDescription("433 error").errorCode(433).build(), null, 433));
    }

    @GetMapping("/getError500")
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public Mono<ResponseEntity<ErrorBody>> getError500(){
        return Mono.just(new ResponseEntity(ErrorBody.builder().errorDescription("500 error").errorCode(500).build(), HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @GetMapping("/getError502")
    @ResponseStatus(code = HttpStatus.BAD_GATEWAY)
    public Mono<ResponseEntity<ErrorBody>> getError502(){
        return Mono.just(new ResponseEntity(ErrorBody.builder().errorDescription("502 error").errorCode(502).build(), HttpStatus.BAD_GATEWAY));
    }
}

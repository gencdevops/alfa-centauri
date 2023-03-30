package com.example.acclientone.config;

import com.example.acclientone.exception.*;
import com.example.acclientone.feign.FeignErrorDecoder;
import com.example.acclientone.model.ErrorBody;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;


@Configuration
public class ServiceTwoClientConfig {
    private final String serviceTwoBaseUrl = "http://localhost:8083/api";

    @Bean
    public FeignErrorDecoder errorDecoder() {
        return new FeignErrorDecoder();
    }

    @Bean
    public WebClient serviceTwoWebClient() {
        return WebClient
                .builder()
                .baseUrl(serviceTwoBaseUrl)
                .filter(ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
                    if (clientResponse.statusCode().isError()) {
                        Mono<ErrorBody> response = clientResponse.bodyToMono(ErrorBody.class);
                        return switch (clientResponse.statusCode().value()){
                            case 400 -> clientResponse.bodyToMono(ErrorBody.class).flatMap(errorBody -> Mono.error(new BadRequestException(errorBody.getErrorDescription())));
                            case 403 -> clientResponse.bodyToMono(ErrorBody.class).flatMap(errorBody -> Mono.error(new ForbiddenException(errorBody.getErrorDescription())));
                            case 404 -> clientResponse.bodyToMono(ErrorBody.class).flatMap(errorBody -> Mono.error(new NotFoundException(errorBody.getErrorDescription())));
                            case 409 -> clientResponse.bodyToMono(ErrorBody.class).flatMap(errorBody -> Mono.error(new ConflictException(errorBody.getErrorDescription())));
                            case 412 -> clientResponse.bodyToMono(ErrorBody.class).flatMap(errorBody -> Mono.error(new PreconditionException(errorBody.getErrorDescription())));
                            case 429 -> clientResponse.bodyToMono(ErrorBody.class).flatMap(errorBody -> Mono.error(new TooManyRequestException(errorBody.getErrorDescription())));
                            case 433 -> clientResponse.bodyToMono(ErrorBody.class).flatMap(errorBody -> Mono.error(new DCBusinessException(errorBody.getErrorDescription(),errorBody.getErrorCode())));
                            case 500 -> clientResponse.bodyToMono(ErrorBody.class).flatMap(errorBody -> Mono.error(new InternalServerException(errorBody.getErrorDescription())));
                            case 502 -> clientResponse.bodyToMono(ErrorBody.class).flatMap(errorBody -> Mono.error(new DCRequestedServiceDownException(errorBody.getErrorDescription(), 434)));
                            default -> Mono.just(clientResponse);
                        };
                    }
                    else {
                        return Mono.just(clientResponse);
                    }
                }))
                .build();
    }

}

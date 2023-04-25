package com.example.acclientone.feign;

import com.example.acclientone.exception.*;

import com.example.acclientone.model.ErrorBody;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
public class FeignErrorDecoder implements ErrorDecoder {
    private ErrorDecoder errorDecoder = new Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        ErrorBody message = null;
        try {
            System.out.println(response.toString());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try (InputStream bodyIs = response.body().asInputStream()) {
            ObjectMapper mapper = new ObjectMapper();
            message = mapper.readValue(bodyIs, ErrorBody.class);
        } catch (IOException e) {
            return new Exception(e.getMessage());
        }

        return switch (response.status()) {
            case 400 -> new BadRequestException(message.getErrorDescription(), 100002);
            case 403 -> new ForbiddenException(message.getErrorDescription(), message.getErrorCode());
            case 404 -> new NotFoundException(message.getErrorDescription(), message.getErrorCode());
            //TODO: Döndürülmesi istenilen özel status kodları eklenecek.
            case 405 -> new MethodNotAllowedException(message.getErrorDescription(), message.getErrorCode());
            case 409 -> new ConflictException(message.getErrorDescription(), message.getErrorCode());
            case 412 -> new PreconditionException(message.getErrorDescription(), message.getErrorCode());
            case 429 -> new TooManyRequestException(message.getErrorDescription(), message.getErrorCode());
            case 433 -> new DCBusinessException(message.getErrorDescription(), message.getErrorCode());
            case 500 -> new InternalServerException(message.getErrorDescription(), message.getErrorCode());
            case 502 -> new DCRequestedServiceDownException(message.getErrorDescription(), 434);
            default -> errorDecoder.decode(methodKey, response);
        };
    }
}
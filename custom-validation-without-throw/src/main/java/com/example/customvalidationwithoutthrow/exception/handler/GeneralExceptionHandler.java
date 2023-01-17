package com.example.customvalidationwithoutthrow.exception.handler;

import com.example.customvalidationwithoutthrow.exception.exceptions.UserAlreadyExistException;
import com.example.customvalidationwithoutthrow.model.ErrorBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@Schema(description = "Exception handling class")
@RestControllerAdvice
@Slf4j
public class GeneralExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error ->{
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        StringBuilder collect = errors.values().stream().collect(StringBuilder::new, StringBuilder::append, StringBuilder::append);

        return ResponseEntity.status(433).body(ErrorBody.builder().errorCode(433).errorMessage(collect.toString()).build());
    }

    @Schema(description = "The user is already exist")
    @ExceptionHandler(UserAlreadyExistException.class)
    public ResponseEntity<ErrorBody> catchUserAlreadyExistException(UserAlreadyExistException exception)  {
        log.error(exception.getLocalizedMessage());
        return ResponseEntity.status(433).body(ErrorBody.builder().errorCode(433).errorMessage(exception.getLocalizedMessage()).build());
    }
}

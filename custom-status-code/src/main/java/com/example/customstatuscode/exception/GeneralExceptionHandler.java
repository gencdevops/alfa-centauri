package com.example.customstatuscode.exception;

import com.example.customstatuscode.model.ErrorBody;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Schema(description = "Exception handling class")
@RestControllerAdvice
@Slf4j
public class GeneralExceptionHandler {

    @Schema(description = "The ID is not valid")
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity badTimeExceptionHandler(UserNotFoundException exception)  {
        log.error("\n" + exception.getLocalizedMessage());
        return ResponseEntity.status(433).body(ErrorBody.builder().errorCode(433).errorMessage(exception.getLocalizedMessage()).build());
    }

}

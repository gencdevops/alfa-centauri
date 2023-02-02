package com.example.propertyencryptionwithjasypt.exception;

import com.example.propertyencryptionwithjasypt.model.ErrorBody;
import io.swagger.v3.oas.annotations.media.Schema;
import org.jasypt.exceptions.EncryptionOperationNotPossibleException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

@Schema(description = "Exception handling class")
@RestControllerAdvice
public class GeneralExceptionHandler {

    @Schema(description = "Text is not valid")
    @ExceptionHandler(EncryptionOperationNotPossibleException.class)
    public ResponseEntity<ErrorBody> catchViolationException(EncryptionOperationNotPossibleException exception)  {
        return ResponseEntity.status(433).body(ErrorBody.builder().errorCode(433).errorMessage("Invalid text").build());
    }
}

package com.example.customvalidationwithoutthrow.controller;

import com.example.customvalidationwithoutthrow.exception.handler.GeneralExceptionHandler;
import com.example.customvalidationwithoutthrow.model.User;
import com.example.customvalidationwithoutthrow.service.abstracts.MessageService;
import com.example.customvalidationwithoutthrow.validation.annotations.TurkcellNumber;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Size;

@RestController
@RequestMapping("/api/v1/messages")
@RequiredArgsConstructor
@Validated
public class MessageController {

    private final MessageService messageService;

    @Operation(summary = "Send message to user operation")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "If number and message are valid",
                    content = {@Content(
                            schema = @Schema(implementation = User.class),
                            mediaType = "application/json")}),
            @ApiResponse(
                    responseCode = "433",
                    description = "If number or message are not valid",
                    content = @Content(
                            schema = @Schema(implementation = GeneralExceptionHandler.class),
                            mediaType = "application/json"))})
    @PostMapping( "/{telNo}")
    public ResponseEntity<Boolean> sendMessageToNumber(
            @Valid
            @PathVariable
            @TurkcellNumber
            String telNo,

            @Valid
            @RequestParam(defaultValue = "Hello")
            @Size(max = 10)
            String message
    ){
        return ResponseEntity.ok(messageService.sendMessageToNumber(message,telNo));
    }
}

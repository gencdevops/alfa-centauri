package com.example.propertyencryptionwithjasypt.controller;

import com.example.propertyencryptionwithjasypt.exception.GeneralExceptionHandler;
import com.example.propertyencryptionwithjasypt.service.JasyptService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/crypt")
@RequiredArgsConstructor
public class EncryptorController {
    private final JasyptService jasyptService;

    @Operation(summary = "Encrypt text with jasypt")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "If the text can be encrypted",
                    content = {@Content(
                            schema = @Schema(implementation = String.class),
                            mediaType = "application/json")})})
    @PostMapping("/encrypt")
    public ResponseEntity<String> encrypt(@RequestBody String str, @RequestParam(defaultValue = "") String password){
        return ResponseEntity.ok(jasyptService.encryptText(str,password));
    }

    @Operation(summary = "Decrypt text with jasypt")
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "If the text can be decrypted",
                    content = {@Content(
                            schema = @Schema(implementation = String.class),
                            mediaType = "application/json")}),
            @ApiResponse(
                    responseCode = "433",
                    description = "If the text can not be decrypted",
                    content = @Content(
                            schema = @Schema(implementation = GeneralExceptionHandler.class),
                            mediaType = "application/json"))})
    @PostMapping("/decrypt")
    public ResponseEntity<String> decrypt(@RequestBody String str, @RequestParam(defaultValue = "") String password){
        return ResponseEntity.ok(jasyptService.decryptText(str,password));
    }
}

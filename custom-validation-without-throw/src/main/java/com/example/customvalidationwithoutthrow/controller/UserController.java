package com.example.customvalidationwithoutthrow.controller;

import com.example.customvalidationwithoutthrow.dto.request.UserRequestDto;
import com.example.customvalidationwithoutthrow.dto.response.UserResponseDto;
import com.example.customvalidationwithoutthrow.exception.handler.GeneralExceptionHandler;
import com.example.customvalidationwithoutthrow.model.User;
import com.example.customvalidationwithoutthrow.service.abstracts.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @Operation(summary = "Post user operation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "User registered in the system",
                    content = {@Content(
                            schema = @Schema(implementation = User.class),
                            mediaType = "application/json")}),
            @ApiResponse(responseCode = "433",
                    description = "User already exist",
                    content = @Content(
                            schema = @Schema(implementation = GeneralExceptionHandler.class),
                            mediaType = "application/json"))})
    @PostMapping
    public ResponseEntity<UserResponseDto> saveUser(@RequestBody @Valid UserRequestDto userRequestDto){
        return ResponseEntity.ok(userService.save(userRequestDto));
    }

}

package com.example.customstatuscode.controller;

import com.example.customstatuscode.dto.UserResponseDto;
import com.example.customstatuscode.exception.GeneralExceptionHandler;
import com.example.customstatuscode.model.User;
import com.example.customstatuscode.service.abstracts.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @Operation(summary = "Get operation user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "A user in exist system",
                    content = {@Content(
                            schema = @Schema(implementation = User.class),
                            mediaType = "application/json")}),
            @ApiResponse(responseCode = "433",
                    description = "User not found",
                    content = @Content(
                            schema = @Schema(implementation = GeneralExceptionHandler.class),
                            mediaType = "application/json"))})
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable Long userId) {
        return ResponseEntity.ok(userService.findById(userId));
    }
}

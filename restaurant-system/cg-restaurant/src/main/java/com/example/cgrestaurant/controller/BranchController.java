package com.example.cgrestaurant.controller;

import com.example.cgrestaurant.dto.request.CreateBranchRequestDto;
import com.example.cgrestaurant.dto.request.UpdateBranchRequestDto;
import com.example.cgrestaurant.dto.response.BranchResponseDto;
import com.example.cgrestaurant.service.BranchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.example.cgrestaurant.contants.RestaurantConstants.*;

@RequiredArgsConstructor
@Tag(name = "Branch Related APIs")
@RestController
@RequestMapping(API_PREFIX + API_VERSION_V1 + API_BRANCHES)
public class BranchController {
    private final BranchService service;


    @Operation(summary = "Create a Branch")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully Branch Created"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PostMapping()
    @ResponseStatus(value = HttpStatus.CREATED)
    public BranchResponseDto createBranch(@Valid @RequestBody CreateBranchRequestDto createBranchRequestDto) {
        return service.createBranch(createBranchRequestDto);
    }

    @Operation(summary = "Retrieve branch according to branch ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully get branch"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public BranchResponseDto getBranchById(@NotNull @PathVariable UUID id) {
        return service.getBranchById(id);
    }

    @Operation(summary = "Get All Branch")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<BranchResponseDto> getAllBranches() {
        return service.getAllBranch();
    }


    @Operation(summary = "Update  Branch")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully Branch Updated"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PutMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public BranchResponseDto updateBranchById(@Valid @PathVariable UUID id, @RequestBody UpdateBranchRequestDto request) {
        return service.updateBranch(id, request);

    }

    @Operation(summary = "Delete Branch")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully Branch Delete"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteBranchById(@PathVariable UUID id) {
        service.deleteBranch(id);
        return ResponseEntity.noContent().build();
    }
}

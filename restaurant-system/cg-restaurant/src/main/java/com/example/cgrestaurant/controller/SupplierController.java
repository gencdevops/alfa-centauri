package com.example.cgrestaurant.controller;

import com.example.cgrestaurant.dto.request.CreateSupplierRequestDto;
import com.example.cgrestaurant.dto.request.UpdateSupplierRequestDto;
import com.example.cgrestaurant.dto.response.SupplierResponseDto;
import com.example.cgrestaurant.service.SupplierService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.example.cgrestaurant.contants.RestaurantConstants.*;

@RequiredArgsConstructor
@Tag(name = "Branch Related APIs")
@RestController
@Slf4j
@RequestMapping(API_PREFIX + API_VERSION_V1 + API_SUPPLIERS)
public class SupplierController {

    private final SupplierService supplierService;

    @Operation(summary = "Create  Supplier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully supplier created"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<SupplierResponseDto> createSupplier(@RequestBody @Valid CreateSupplierRequestDto createSupplierRequestDto) {
        return ResponseEntity.ok(supplierService.createSupplier(createSupplierRequestDto));
    }

    @Operation(summary = "Get Supplier According To Supplier ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully get supplier"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<SupplierResponseDto> getSupplierById(@PathVariable UUID id) {
        return ResponseEntity.ok(supplierService.getSupplierById(id));
    }

    @Operation(summary = "Get All Suppliers  Branch")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping
    public ResponseEntity<List<SupplierResponseDto>> getAllSuppliers() {
        return ResponseEntity.ok(supplierService.getAllSupplier());
    }

    @Operation(summary = "Update  Supplier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully Supplier Updated"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PutMapping("/{id}")
    public ResponseEntity<String> updateSupplierById(@PathVariable UUID id, @RequestBody UpdateSupplierRequestDto request) {
        return ResponseEntity.ok(supplierService.updateSupplier(id, request));
    }

    @Operation(summary = "Delete  Supplier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully supplier deleted"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public String deleteSupplierById(@PathVariable UUID id) {
        return supplierService.deleteSupplier(id);
    }
}

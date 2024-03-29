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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.example.cgrestaurant.contants.RestaurantConstants.*;

@RequiredArgsConstructor
@Tag(name = "Supplier Related APIs")
@RestController
@Slf4j
@RequestMapping(API_PREFIX + API_VERSION_V1 + API_SUPPLIERS)
public class SupplierController {

    private final SupplierService supplierService;

    @Operation(summary = "Create  Supplier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully supplier created"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public SupplierResponseDto createSupplier(@RequestBody @Valid CreateSupplierRequestDto createSupplierRequestDto) {
        return supplierService.createSupplier(createSupplierRequestDto);
    }

    @Operation(summary = "Get Supplier According To Supplier ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully get supplier"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public SupplierResponseDto getSupplierById(@PathVariable UUID id) {
        return supplierService.getSupplierByIdConvertedSupplierResponseDto(id);
    }

    @Operation(summary = "Get All Suppliers  Branch")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<SupplierResponseDto> getAllSuppliers() {
        return supplierService.getAllSupplier();
    }

    @Operation(summary = "Update  Supplier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully Supplier Updated"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PutMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public SupplierResponseDto updateSupplierById(@Valid @PathVariable UUID id, @RequestBody UpdateSupplierRequestDto request) {
        return supplierService.updateSupplier(id, request);
    }

    @Operation(summary = "Delete  Supplier")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successfully supplier deleted"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteSupplierById(@PathVariable UUID id) {
        supplierService.deleteSupplier(id);
        return ResponseEntity.noContent().build();
    }
}

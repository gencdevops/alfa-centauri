package com.example.cgrestaurant.controller;

import com.example.cgrestaurant.dto.request.CreateProductRequestDto;
import com.example.cgrestaurant.dto.request.UpdateProductRequestDto;
import com.example.cgrestaurant.dto.response.ProductResponseDto;
import com.example.cgrestaurant.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.example.cgrestaurant.contants.RestaurantConstants.*;

@RequiredArgsConstructor
@Tag(name = "Product Related APIs")
@RestController
@RequestMapping(API_PREFIX + API_VERSION_V1 + API_PRODUCTS)
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "Create  Product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully Product Updated"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<ProductResponseDto> createProduct(@Valid @RequestBody CreateProductRequestDto createProductRequestDto) {
        return ResponseEntity.ok(productService.createProduct(createProductRequestDto));
    }

    @Operation(summary = "Update  Branch")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully Branch Updated"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @Operation(summary = "Get ALl Products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get all products"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProduct());
    }

    @Operation(summary = "Update  Product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully Product Updated"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDto> updateProductById(@Valid @PathVariable Long id, @RequestBody UpdateProductRequestDto request) {
        return ResponseEntity.ok(productService.updateProduct(id, request));
    }

    @Operation(summary = "Update  Branch")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully Branch Updated"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public ResponseEntity<Void> deleteProductById(@PathVariable Long id) {
         productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }

}

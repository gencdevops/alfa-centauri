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
    public ProductResponseDto createProduct(@Valid @RequestBody CreateProductRequestDto createProductRequestDto) {
        return productService.createProduct(createProductRequestDto);
    }

    @Operation(summary = "Update  Branch")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully Branch Updated"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public ProductResponseDto getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @Operation(summary = "Get ALl Products")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Get all products"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    public List<ProductResponseDto> getAllProducts() {
        return productService.getAllProduct();
    }

    @Operation(summary = "Update  Product")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully Product Updated"),
            @ApiResponse(responseCode = "404", description = "Not Found"),
            @ApiResponse(responseCode = "500", description = "Internal Server Error")
    })
    @PutMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public ProductResponseDto updateProductById(@Valid @PathVariable Long id, @RequestBody UpdateProductRequestDto request) {
        return productService.updateProduct(id, request);
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

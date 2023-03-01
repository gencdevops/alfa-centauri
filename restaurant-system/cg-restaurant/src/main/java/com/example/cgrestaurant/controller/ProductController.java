package com.example.cgrestaurant.controller;

import com.example.cgrestaurant.dto.request.CreateProductRequest;
import com.example.cgrestaurant.dto.request.UpdateProductRequest;
import com.example.cgrestaurant.dto.response.ProductDto;
import com.example.cgrestaurant.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/product")
public class ProductController {

    private final ProductService service;

    @PostMapping
    public ResponseEntity<String> createProduct(@RequestBody CreateProductRequest request) {
        return ResponseEntity.ok(service.createProduct(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getProductById(id));
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        return ResponseEntity.ok(service.getAllProduct());
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateProductById(@PathVariable UUID id, @RequestBody UpdateProductRequest request) {
        return ResponseEntity.ok(service.updateProduct(id, request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public String deleteProductById(@PathVariable UUID id) {
        return service.deleteProduct(id);
    }
}

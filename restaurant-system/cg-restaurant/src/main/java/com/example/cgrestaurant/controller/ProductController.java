package com.example.cgrestaurant.controller;

import com.example.cgrestaurant.dto.request.CreateBranchRequest;
import com.example.cgrestaurant.dto.request.CreateProductRequest;
import com.example.cgrestaurant.dto.response.BranchDto;
import com.example.cgrestaurant.dto.response.ProductDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/api/product")
public class ProductController {

    @PostMapping
    public ResponseEntity<String> createProduct(CreateProductRequest request) {
        return null;
    }

    @GetMapping
    public ResponseEntity<ProductDto> getProductById(UUID id) {
        return null;
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        return null;
    }

    @PutMapping
    public ResponseEntity<String> updateProductById(UUID id) {
        return null;
    }

    @DeleteMapping
    public ResponseEntity<String> deleteProductById(UUID id) {
        return null;
    }
}

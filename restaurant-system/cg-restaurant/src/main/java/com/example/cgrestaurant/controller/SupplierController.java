package com.example.cgrestaurant.controller;

import com.example.cgrestaurant.dto.request.CreateSupplierRequest;
import com.example.cgrestaurant.dto.response.SupplierDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/api/supplier")
public class SupplierController {

    @PostMapping
    public ResponseEntity<String> createSupplier(CreateSupplierRequest request) {
        return null;
    }

    @GetMapping
    public ResponseEntity<SupplierDto> getSupplierById(UUID id) {
        return null;
    }

    @GetMapping("/all")
    public ResponseEntity<List<SupplierDto>> getAllSuppliers() {
        return null;
    }

    @PutMapping
    public ResponseEntity<String> updateSupplierById(UUID id) {
        return null;
    }

    @DeleteMapping
    public ResponseEntity<String> deleteSupplierById(UUID id) {
        return null;
    }
}

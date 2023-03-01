package com.example.cgrestaurant.controller;

import com.example.cgrestaurant.dto.request.CreateSupplierRequest;
import com.example.cgrestaurant.dto.response.SupplierDto;
import com.example.cgrestaurant.model.Supplier;
import com.example.cgrestaurant.repository.SupplierRepository;
import com.example.cgrestaurant.service.SupplierService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api/v1/supplier")
public class SupplierController {

    private final SupplierService service;
//    private final SupplierRepository repository;

    @PostMapping
    public ResponseEntity<String> createSupplier(@RequestBody CreateSupplierRequest request) {
        return ResponseEntity.ok(service.createSupplier(request));
    }

    @PostMapping("/test")
    public ResponseEntity<String> test() {
//        Supplier supplier = Supplier.builder()
//                .supplierName("test")
//                .build();
//        log.warn(supplier.toString());
//        repository.save(supplier);
//        return ResponseEntity.ok(supplier.toString());
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

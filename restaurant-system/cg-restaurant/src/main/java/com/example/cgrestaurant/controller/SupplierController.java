package com.example.cgrestaurant.controller;

import com.example.cgrestaurant.dto.request.CreateSupplierRequest;
import com.example.cgrestaurant.dto.request.UpdateSupplierRequest;
import com.example.cgrestaurant.dto.response.SupplierDto;
import com.example.cgrestaurant.service.SupplierService;
import com.example.cgrestaurant.service.TestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api/v1/supplier")
public class SupplierController {

    private final SupplierService service;
    private final TestService testService;

    @GetMapping("/test")
    public String testtt() {
        return testService.createOrderFeign();
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public String createSupplier(@RequestBody CreateSupplierRequest request) {
        return service.createSupplier(request);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SupplierDto> getSupplierById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getSupplierById(id));
    }

    @GetMapping
    public ResponseEntity<List<SupplierDto>> getAllSuppliers() {
        return ResponseEntity.ok(service.getAllSupplier());
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateSupplierById(@PathVariable UUID id, @RequestBody UpdateSupplierRequest request) {
        return ResponseEntity.ok(service.updateSupplier(id, request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public String deleteSupplierById(@PathVariable UUID id) {
        return service.deleteSupplier(id);
    }
}

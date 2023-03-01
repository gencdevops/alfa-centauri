package com.example.cgrestaurant.controller;

import com.example.cgrestaurant.dto.request.CreateBranchRequest;
import com.example.cgrestaurant.dto.request.CreateSupplierRequest;
import com.example.cgrestaurant.dto.response.BranchDto;
import com.example.cgrestaurant.dto.response.SupplierDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/v1/api/branch")
public class BranchController {

    @PostMapping
    public ResponseEntity<String> createBranch(CreateBranchRequest request) {
        return null;
    }

    @GetMapping
    public ResponseEntity<BranchDto> getBranchById(UUID id) {
        return null;
    }

    @GetMapping
    public ResponseEntity<List<BranchDto>> getAllBranches() {
        return null;
    }

    @PutMapping
    public ResponseEntity<String> updateBranchById(UUID id) {
        return null;
    }

    @DeleteMapping
    public ResponseEntity<String> deleteBranchById(UUID id) {
        return null;
    }
}

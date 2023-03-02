package com.example.cgrestaurant.controller;

import com.example.cgrestaurant.dto.request.CreateBranchRequest;
import com.example.cgrestaurant.dto.request.UpdateBranchRequest;
import com.example.cgrestaurant.dto.response.BranchDto;
import com.example.cgrestaurant.service.BranchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/branch")
public class BranchController {

    private final BranchService service;

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    public String createBranch(@RequestBody CreateBranchRequest request) {
        return service.createBranch(request);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BranchDto> getBranchById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getBranchById(id));
    }

    @GetMapping
    public ResponseEntity<List<BranchDto>> getAllBranches() {
        return ResponseEntity.ok(service.getAllBranch());
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateBranchById(@PathVariable UUID id, @RequestBody UpdateBranchRequest request) {
        return ResponseEntity.ok(service.updateBranch(id, request));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public String deleteBranchById(@PathVariable UUID id) {
        return service.deleteBranch(id);
    }
}

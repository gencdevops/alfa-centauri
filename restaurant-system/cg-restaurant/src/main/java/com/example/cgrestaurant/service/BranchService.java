package com.example.cgrestaurant.service;

import com.example.cgrestaurant.dto.request.CreateBranchRequestDto;
import com.example.cgrestaurant.dto.request.UpdateBranchRequestDto;
import com.example.cgrestaurant.dto.response.BranchResponseDto;
import com.example.cgrestaurant.exception.BranchNotFoundException;
import com.example.cgrestaurant.mapper.BranchMapper;
import com.example.cgrestaurant.model.Branch;
import com.example.cgrestaurant.repository.BranchRepository;
import com.example.cgrestaurant.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Service
public class BranchService {

    private final BranchRepository branchRepository;

    private final SupplierService supplierService;

    private final BranchMapper branchMapper;

    public BranchResponseDto createBranch(CreateBranchRequestDto createBranchRequestDto) {
        Branch createdBranch = branchMapper.convertBranchFromCreateBranchRequestDto(createBranchRequestDto);
        createdBranch.setSupplier(supplierService.getSupplierByID(createBranchRequestDto.supplierId()));
        createdBranch = branchRepository.save(createdBranch);
        log.info("created branch : " + createdBranch);
        return branchMapper.convertBranchResponseDtoFromBranch(createdBranch);
    }

    public BranchResponseDto getBranchById(UUID id) {
        return branchRepository.findById(id)
                .map(branchMapper::convertBranchResponseDtoFromBranch)
                .orElseThrow(() -> new BranchNotFoundException("Branch not found"));
    }

    public List<BranchResponseDto> getAllBranch() {
        return branchRepository.findAll()
                .stream()
                .map(branchMapper::convertBranchResponseDtoFromBranch)
                .toList();
    }

    public BranchResponseDto updateBranch(UUID id, UpdateBranchRequestDto request) {
        Branch branch = branchRepository.findById(id).orElseThrow(() -> new BranchNotFoundException("Branch not found."));
        branch.setBranchName(request.branchName());

      return   branchMapper.convertBranchResponseDtoFromBranch(branchRepository.save(branch));
    }

    public void deleteBranch(UUID id) {
         branchRepository.deleteById(id);
    }
}

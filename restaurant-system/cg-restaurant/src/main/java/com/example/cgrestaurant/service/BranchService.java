package com.example.cgrestaurant.service;

import com.example.cgrestaurant.dto.request.CreateBranchRequestDto;
import com.example.cgrestaurant.dto.request.UpdateBranchRequestDto;
import com.example.cgrestaurant.dto.response.BranchResponseDto;
import com.example.cgrestaurant.exception.BranchNotFoundException;
import com.example.cgrestaurant.mapper.BranchMapper;
import com.example.cgrestaurant.model.Branch;
import com.example.cgrestaurant.repository.BranchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Service
public class BranchService {

    private final BranchRepository repository;

    private final BranchMapper branchMapper;

    public String createBranch(CreateBranchRequestDto request) {
        Branch created = branchMapper.convertBranchFromCreateBranchRequestDto(request);
        created = repository.save(created);
        log.info("created: " + created);
        return "Branch başarıyla oluşturuldu. (" + created.getBranchId() + ")";
    }

    public BranchResponseDto getBranchById(UUID id) {
        return repository.findById(id)
                .map(branchMapper::convertBranchResponseDtoFromBrach)
                .orElseThrow(() -> new BranchNotFoundException("Branch bulunamadı."));
    }

    public List<BranchResponseDto> getAllBranch() {
        return repository.findAll()
                .stream()
                .map(branchMapper::convertBranchResponseDtoFromBrach)
                .toList();
    }

    public BranchResponseDto updateBranch(UUID id, UpdateBranchRequestDto request) {
        Branch branch = repository.findById(id).orElseThrow(() -> new BranchNotFoundException("Branch bulunamadı."));
        branch.setBranchName(request.branchName());

      return   branchMapper.convertBranchResponseDtoFromBrach(repository.save(branch));
    }

    public String deleteBranch(UUID id) {
        return repository.findById(id)
                .map(branch -> {
                    repository.deleteById(id);
                    return id + ": nolu branch başarıyla silindi.";
                })
                .orElseThrow(() -> new BranchNotFoundException("Branch bulunamadı."));
    }
}

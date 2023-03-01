package com.example.cgrestaurant.service;

import com.example.cgrestaurant.dto.request.CreateBranchRequest;
import com.example.cgrestaurant.dto.request.UpdateBranchRequest;
import com.example.cgrestaurant.dto.response.BranchDto;
import com.example.cgrestaurant.exception.BranchNotFoundException;
import com.example.cgrestaurant.mapper.BranchMapper;
import com.example.cgrestaurant.model.Branch;
import com.example.cgrestaurant.repository.BranchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Service
public class BranchService {

    private final BranchRepository repository;

    private final BranchMapper mapper;

    public String createBranch(CreateBranchRequest request) {
        Branch created = mapper.toBranchFromCreateBranchRequest(request);
        created.setCreateDate(LocalDate.now());
        created = repository.save(created);
        log.info("created: " + created);
        return "Branch başarıyla oluşturuldu. (" + created.getBranchId() + ")";
    }

    public BranchDto getBranchById(UUID id) {
        return repository.findById(id)
                .map(mapper::toBranchDto)
                .orElseThrow(() -> new BranchNotFoundException("Branch bulunamadı."));
    }

    public List<BranchDto> getAllBranch() {
        return repository.findAll()
                .stream()
                .map(mapper::toBranchDto)
                .toList();
    }

    public String updateBranch(UUID id, UpdateBranchRequest request) {
        repository.findById(id)
                .map(branch -> {
                    branch.setBranchName(request.branchName());
                    branch.setUpdateDate(LocalDate.now());
                    repository.save(branch);
                    return branch;
                })
                .orElseThrow(() -> new BranchNotFoundException("Branch bulunamadı."));
        return "Branch başarıyla güncellendi.";
    }

    public String deleteBranch(UUID id) {
        return repository.findById(id)
                .map(branch -> {
                    repository.deleteById(id);
                    return id + ": nolu branch başarıyla silindi.";})
                .orElseThrow(() -> new BranchNotFoundException("Branch bulunamadı."));
    }
}

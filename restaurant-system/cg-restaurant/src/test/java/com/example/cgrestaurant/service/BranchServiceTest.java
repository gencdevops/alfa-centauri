package com.example.cgrestaurant.service;

import com.example.cgrestaurant.dto.request.CreateBranchRequestDto;
import com.example.cgrestaurant.dto.request.UpdateBranchRequestDto;
import com.example.cgrestaurant.dto.response.BranchResponseDto;
import com.example.cgrestaurant.exception.BranchNotFoundException;
import com.example.cgrestaurant.mapper.BranchMapper;
import com.example.cgrestaurant.model.Branch;
import com.example.cgrestaurant.model.Supplier;
import com.example.cgrestaurant.repository.BranchRepository;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BranchServiceTest {

    @InjectMocks
    private BranchService branchService;
    @Mock private BranchRepository branchRepository;

    @Mock private SupplierService supplierService;

    @Mock private BranchMapper branchMapper;

    @Test
    void shouldReturnBranchResponseWhenCreateBranch() {
        String branchName = RandomStringUtils.random(5);
        CreateBranchRequestDto requestDto = new CreateBranchRequestDto(branchName,null, UUID.randomUUID());
        Branch branch = new Branch();
        Supplier supplier = new Supplier();

        when(branchMapper.convertBranchFromCreateBranchRequestDto(requestDto)).thenReturn(branch);
        when(supplierService.getSupplierByID(requestDto.supplierId())).thenReturn(supplier);
        when(branchRepository.save(branch)).thenReturn(branch);
        when(branchMapper.convertBranchResponseDtoFromBranch(branch)).thenReturn(new BranchResponseDto(branchName));

        BranchResponseDto responseDto = branchService.createBranch(requestDto);

        verify(branchMapper).convertBranchFromCreateBranchRequestDto(requestDto);
        verify(supplierService).getSupplierByID(requestDto.supplierId());
        verify(branchRepository).save(branch);
        verify(branchMapper).convertBranchResponseDtoFromBranch(branch);
        assertNotNull(responseDto);
        assertEquals(responseDto, branchMapper.convertBranchResponseDtoFromBranch(branch));
    }

    @Test
    public void shouldThrowExceptionWhenBranchByIdNotFound() {
        UUID branchId = UUID.randomUUID();
        when(branchRepository.findById(branchId)).thenReturn(Optional.empty());
        assertThrows(BranchNotFoundException.class, () -> branchService.getBranchById(branchId));
    }

    @Test
    void shouldReturnBranchResponseWhenExistById() {
        UUID id = UUID.randomUUID();
        Branch branch = Branch.builder()
                .branchId(id)
                .branchName(RandomStringUtils.random(5))
                .build();

        when(branchRepository.findById(id)).thenReturn(Optional.of(branch));
        when(branchMapper.convertBranchResponseDtoFromBranch(branch)).thenReturn(new BranchResponseDto(branch.getBranchName()));

        BranchResponseDto responseDto = branchService.getBranchById(id);

        assertEquals(branch.getBranchName(), responseDto.branchName());
        verify(branchRepository).findById(id);
    }

    @Test
    void shouldReturnAllBranches() {
        BranchResponseDto responseDto = new BranchResponseDto(RandomStringUtils.random(5));
        when(branchMapper.convertBranchResponseDtoFromBranch(any(Branch.class))).thenReturn(responseDto);
        when(branchRepository.findAll()).thenReturn(List.of(new Branch()));

        List<BranchResponseDto> actual = branchService.getAllBranch();
        verify(branchRepository).findAll();
        assertNotNull(actual);
        assertEquals(1, actual.size());
        assertEquals(responseDto, actual.get(0));
    }

    @Test
    void shouldReturnBranchResponseWhenUpdateBranch() {
        UUID id = UUID.randomUUID();
        String branchName = RandomStringUtils.random(5);
        Branch branch = Branch.builder()
                .branchId(id)
                .branchName(branchName)
                .build();

        UpdateBranchRequestDto requestDto = new UpdateBranchRequestDto(branchName);


        when(branchRepository.findById(id)).thenReturn(Optional.of(branch));
        when(branchRepository.save(branch)).thenReturn(branch);
        when(branchMapper.convertBranchResponseDtoFromBranch(branch)).thenReturn(new BranchResponseDto(branchName));

        BranchResponseDto responseDto = branchService.updateBranch(id, requestDto);

        assertEquals(branchName, responseDto.branchName());
        verify(branchRepository).findById(id);
        verify(branchRepository).save(branch);
    }

    @Test
    void shouldThrowExceptionWhenUpdateBranch() {
        UUID id = UUID.randomUUID();
        when(branchRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(BranchNotFoundException.class, () -> branchService.updateBranch(id, new UpdateBranchRequestDto(RandomStringUtils.random(5))));
    }

    @Test
    void shouldDeleteBranch() {
        UUID id = UUID.randomUUID();
        branchService.deleteBranch(id);
        verify(branchRepository).deleteById(id);
    }
}
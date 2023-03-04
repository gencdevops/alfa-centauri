package com.example.cgrestaurant.service;

import com.example.cgrestaurant.dto.request.CreateSupplierRequestDto;
import com.example.cgrestaurant.dto.request.UpdateSupplierRequestDto;
import com.example.cgrestaurant.dto.response.SupplierResponseDto;
import com.example.cgrestaurant.exception.ProductNotFoundException;
import com.example.cgrestaurant.exception.SupplierNotFoundException;
import com.example.cgrestaurant.mapper.SupplierMapper;
import com.example.cgrestaurant.model.Product;
import com.example.cgrestaurant.model.Supplier;
import com.example.cgrestaurant.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@RequiredArgsConstructor
@Slf4j
@Service
public class SupplierService {

    private final SupplierRepository supplierRepository;

    private final SupplierMapper supplierMapper;

    public SupplierResponseDto createSupplier(CreateSupplierRequestDto request) {
        Supplier created = supplierMapper.convertSupplierFromCreateSupplierRequestDto(request);
        created = supplierRepository.save(created);
        log.info("created: " + created);
        return supplierMapper.convertSupplierResponseDtoFromSupplier(created);
    }

    public SupplierResponseDto getSupplierByIdConvertedSupplierResponseDto(UUID id) {
        return supplierMapper.convertSupplierResponseDtoFromSupplier(getSupplierByID(id));
    }

    public Supplier getSupplierByID(UUID id) {
        return supplierRepository.findById(id)
                .orElseThrow(() -> new SupplierNotFoundException("Supplier not found."));
    }

    public List<SupplierResponseDto> getAllSupplier() {
        return supplierRepository.findAll()
                .stream()
                .map(supplierMapper::convertSupplierResponseDtoFromSupplier)
                .toList();
    }

    public SupplierResponseDto updateSupplier(UUID id, UpdateSupplierRequestDto updateSupplierRequestDto) {
        Supplier supplier = supplierRepository.findById(id).orElseThrow(() -> new SupplierNotFoundException("Supplier not found."));
       supplier.setSupplierName(updateSupplierRequestDto.supplierName());
        return   supplierMapper.convertSupplierResponseDtoFromSupplier(supplierRepository.save(supplier));
    }

    public void deleteSupplier(UUID id) {
       supplierRepository.deleteById(id);
    }

}

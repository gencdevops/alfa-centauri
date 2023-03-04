package com.example.cgrestaurant.service;

import com.example.cgrestaurant.dto.request.CreateSupplierRequestDto;
import com.example.cgrestaurant.dto.request.UpdateSupplierRequestDto;
import com.example.cgrestaurant.dto.response.SupplierResponseDto;
import com.example.cgrestaurant.exception.SupplierNotFoundException;
import com.example.cgrestaurant.mapper.SupplierMapper;
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

    public SupplierResponseDto getSupplierById(UUID id) {
        return supplierRepository.findById(id)
                .map(supplierMapper::convertSupplierResponseDtoFromSupplier)
                .orElseThrow(() -> new SupplierNotFoundException("Supplier bulunamadı."));
    }

    public List<SupplierResponseDto> getAllSupplier() {
        return supplierRepository.findAll()
                .stream()
                .map(supplierMapper::convertSupplierResponseDtoFromSupplier)
                .toList();
    }

    public String updateSupplier(UUID id, UpdateSupplierRequestDto request) {
        supplierRepository.findById(id)
                .map(supplier -> {
                    supplier.setSupplierName(request.supplierName());
                    supplierRepository.save(supplier);
                    return supplier;
                })
                .orElseThrow(() -> new SupplierNotFoundException("Supplier bulunamadı."));
        return "Supplier başarıyla güncellendi.";
    }

    public String deleteSupplier(UUID id) {
        supplierRepository.findById(id)
                .map(supplier -> {
                    supplierRepository.deleteById(id);
                    return supplier; })
                .orElseThrow(() -> new SupplierNotFoundException("Supplier bulunamadı."));
        return id + ": nolu supplier başarıyla silindi.";
    }

}

package com.example.cgrestaurant.service;

import com.example.cgrestaurant.dto.request.CreateSupplierRequest;
import com.example.cgrestaurant.dto.request.UpdateSupplierRequest;
import com.example.cgrestaurant.dto.response.SupplierDto;
import com.example.cgrestaurant.exception.SupplierNotFoundException;
import com.example.cgrestaurant.mapper.SupplierMapper;
import com.example.cgrestaurant.model.Supplier;
import com.example.cgrestaurant.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


@RequiredArgsConstructor
@Slf4j
@Service
public class SupplierService {

    private final SupplierRepository repository;

    private final SupplierMapper mapper;

    public String createSupplier(CreateSupplierRequest request) {
        Supplier created = mapper.toSupplierFromCreateSupplierRequest(request);
        created = repository.save(created);
        log.info("created: " + created);
        return "Supplier başarıyla oluşturuldu. (" + created.getSupplierId() + ")";
    }

    public SupplierDto getSupplierById(UUID id) {
        return repository.findById(id)
                .map(mapper::toSupplierDto)
                .orElseThrow(() -> new SupplierNotFoundException("Supplier bulunamadı."));
    }

    public List<SupplierDto> getAllSupplier() {
        return repository.findAll()
                .stream()
                .map(mapper::toSupplierDto)
                .toList();
    }

    public String updateSupplier(UUID id, UpdateSupplierRequest request) {
        repository.findById(id)
                .map(supplier -> {
                    supplier.setSupplierName(request.supplierName());
                    repository.save(supplier);
                    return supplier;
                })
                .orElseThrow(() -> new SupplierNotFoundException("Supplier bulunamadı."));
        return "Supplier başarıyla güncellendi.";
    }

    public String deleteSupplier(UUID id) {
        repository.findById(id)
                .map(supplier -> {
                    repository.deleteById(id);
                    return supplier; })
                .orElseThrow(() -> new SupplierNotFoundException("Supplier bulunamadı."));
        return id + ": nolu supplier başarıyla silindi.";
    }

}

package com.example.cgrestaurant.service;

import com.example.cgrestaurant.dto.request.CreateSupplierRequest;
import com.example.cgrestaurant.mapper.SupplierMapper;
import com.example.cgrestaurant.model.Supplier;
import com.example.cgrestaurant.repository.SupplierRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;


@RequiredArgsConstructor
@Slf4j
@Service
public class SupplierService {

//    private final SupplierRepository repository;

    private final SupplierMapper mapper;

    public String createSupplier(CreateSupplierRequest request) {
//        Supplier created = mapper.toSupplierFromCreateSupplierRequest(request);
//        created.setCreateDate(LocalDate.now());
//        log.warn(repository.findAll().toString());
//        repository.save(created);
//        log.info("created: " + created);

        return "Supplier başarıyla oluşturuldu.";
    }

}

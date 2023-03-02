package com.example.cgrestaurant.service;

import com.example.cgrestaurant.dto.request.CreateProductRequest;
import com.example.cgrestaurant.dto.request.UpdateProductRequest;
import com.example.cgrestaurant.dto.response.ProductDto;
import com.example.cgrestaurant.exception.ProductNotFoundException;
import com.example.cgrestaurant.mapper.ProductMapper;
import com.example.cgrestaurant.model.Product;
import com.example.cgrestaurant.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Service
public class ProductService {

    private final ProductRepository repository;
    private final ProductMapper mapper;

    public String createProduct(CreateProductRequest request) {
        Product created = mapper.toProductFromCreateProductRequest(request);
        created.setCreateDate(LocalDate.now());
        created = repository.save(created);
        log.info("created: " + created);
        return "Product başarıyla oluşturuldu. (" + created.getProductId() + ")";
    }

    public ProductDto getProductById(UUID id) {
        return repository.findById(id)
                .map(mapper::toProductDto)
                .orElseThrow(() -> new ProductNotFoundException("Product bulunamadı."));
    }

    public List<ProductDto> getAllProduct() {
        return repository.findAll()
                .stream()
                .map(mapper::toProductDto)
                .toList();
    }

    public String updateProduct(UUID id, UpdateProductRequest request) {
        repository.findById(id)
                .map(product -> {
                    product.setProductName(request.productName());
                    product.setUpdateDate(LocalDate.now());
                    repository.save(product);
                    return product;
                })
                .orElseThrow(() -> new ProductNotFoundException("Product bulunamadı."));
        return "Product başarıyla güncellendi.";
    }

    public String deleteProduct(UUID id) {
        return repository.findById(id)
                .map(product -> {
                    repository.deleteById(id);
                    return id + ": nolu product başarıyla silindi.";})
                .orElseThrow(() -> new ProductNotFoundException("Product bulunamadı."));
    }
}

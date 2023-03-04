package com.example.cgrestaurant.service;

import com.example.cgrestaurant.dto.request.CreateProductRequestDto;
import com.example.cgrestaurant.dto.request.UpdateProductRequestDto;
import com.example.cgrestaurant.dto.response.ProductResponseDto;
import com.example.cgrestaurant.exception.ProductNotFoundException;
import com.example.cgrestaurant.mapper.ProductMapper;
import com.example.cgrestaurant.model.Product;
import com.example.cgrestaurant.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Slf4j
@Service
public class ProductService {

    private final ProductRepository repository;
    private final ProductMapper mapper;

    public String createProduct(CreateProductRequestDto request) {
        Product created = mapper.toProductFromCreateProductRequest(request);
        created = repository.save(created);
        log.info("created: " + created);
        return "Product başarıyla oluşturuldu. (" + created.getProductId() + ")";
    }

    public ProductResponseDto getProductById(Long id) {
        return repository.findById(id)
                .map(mapper::toProductDto)
                .orElseThrow(() -> new ProductNotFoundException("Product bulunamadı."));
    }

    public List<ProductResponseDto> getAllProduct() {
        return repository.findAll()
                .stream()
                .map(mapper::toProductDto)
                .toList();
    }

    public String updateProduct(Long id, UpdateProductRequestDto request) {
        repository.findById(id)
                .map(product -> {
                    product.setProductName(request.productName());
                    repository.save(product);
                    return product;
                })
                .orElseThrow(() -> new ProductNotFoundException("Product bulunamadı."));
        return "Product başarıyla güncellendi.";
    }

    public String deleteProduct(Long id) {
        return repository.findById(id)
                .map(product -> {
                    repository.deleteById(id);
                    return id + ": nolu product başarıyla silindi.";})
                .orElseThrow(() -> new ProductNotFoundException("Product bulunamadı."));
    }
}

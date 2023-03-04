package com.example.cgrestaurant.mapper;

import com.example.cgrestaurant.dto.request.CreateProductRequestDto;
import com.example.cgrestaurant.dto.response.ProductResponseDto;
import com.example.cgrestaurant.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(implementationName = "ProductMapperImpl", componentModel = "spring", imports = Product.class)
public interface ProductMapper {

    @Mapping(target = "supplier", ignore = true)
    Product toProductFromCreateProductRequest(CreateProductRequestDto request);

    ProductResponseDto toProductDto(Product product);
}

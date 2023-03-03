package com.example.cgrestaurant.mapper;

import com.example.cgrestaurant.dto.request.CreateProductRequest;
import com.example.cgrestaurant.dto.response.ProductDto;
import com.example.cgrestaurant.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(implementationName = "ProductMapperImpl", componentModel = "spring", imports = Product.class)
public interface ProductMapper {

    @Mapping(target = "supplier", ignore = true)
    Product toProductFromCreateProductRequest(CreateProductRequest request);

    ProductDto toProductDto(Product product);
}

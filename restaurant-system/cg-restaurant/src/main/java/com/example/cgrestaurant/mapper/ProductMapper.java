package com.example.cgrestaurant.mapper;

import com.example.cgrestaurant.dto.request.CreateProductRequestDto;
import com.example.cgrestaurant.dto.response.ProductResponseDto;
import com.example.cgrestaurant.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(implementationName = "ProductMapperImpl", componentModel = "spring", imports = Product.class)
public interface ProductMapper {


    Product convertProductFromCreateProductRequestDto(CreateProductRequestDto request);

    ProductResponseDto convertProductResponseDtoFromProduct(Product product);
}

package com.example.cgrestaurant.mapper;

import com.example.cgcommon.dto.response.ProductPriceResponseDto;
import com.example.cgrestaurant.dto.request.CreateProductRequestDto;
import com.example.cgrestaurant.dto.response.ProductResponseDto;
import com.example.cgrestaurant.model.Product;
import com.example.cgrestaurant.model.ProductPrice;
import org.mapstruct.Mapper;

@Mapper(implementationName = "ProductMapperImpl", componentModel = "spring", imports = Product.class)
public interface ProductMapper {

    Product convertProductFromCreateProductRequestDto(CreateProductRequestDto request);

    ProductResponseDto convertProductResponseDtoFromProduct(Product product);
    ProductPriceResponseDto convertProductPriceResponseDtoFromProductPrice(ProductPrice productPrice);
}

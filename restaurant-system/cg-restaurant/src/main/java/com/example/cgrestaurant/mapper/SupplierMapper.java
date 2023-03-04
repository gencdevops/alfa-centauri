package com.example.cgrestaurant.mapper;

import com.example.cgrestaurant.dto.request.CreateSupplierRequestDto;
import com.example.cgrestaurant.dto.response.SupplierResponseDto;
import com.example.cgrestaurant.model.Supplier;
import org.mapstruct.Mapper;

@Mapper(implementationName = "SupplierMapperImpl", componentModel = "spring", imports = Supplier.class)
public interface SupplierMapper {

    Supplier toSupplierFromCreateSupplierRequest(CreateSupplierRequestDto request);

    SupplierResponseDto toSupplierDto(Supplier supplier);
}

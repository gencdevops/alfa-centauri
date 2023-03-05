package com.example.cgrestaurant.mapper;

import com.example.cgrestaurant.dto.request.CreateSupplierRequestDto;
import com.example.cgrestaurant.dto.response.SupplierResponseDto;
import com.example.cgrestaurant.model.Supplier;
import org.mapstruct.Mapper;

@Mapper(implementationName = "SupplierMapperImpl", componentModel = "spring", imports = Supplier.class)
public interface SupplierMapper {

    Supplier convertSupplierFromCreateSupplierRequestDto(CreateSupplierRequestDto request);

    SupplierResponseDto convertSupplierResponseDtoFromSupplier(Supplier supplier);
}

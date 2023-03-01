package com.example.cgrestaurant.mapper;

import com.example.cgrestaurant.dto.request.CreateSupplierRequest;
import com.example.cgrestaurant.dto.response.SupplierDto;
import com.example.cgrestaurant.model.Supplier;
import org.mapstruct.Mapper;

@Mapper(implementationName = "SupplierMapperImpl", componentModel = "spring", imports = Supplier.class)
public interface SupplierMapper {

    Supplier toSupplierFromCreateSupplierRequest(CreateSupplierRequest request);

    SupplierDto toSupplierDto(Supplier supplier);
}

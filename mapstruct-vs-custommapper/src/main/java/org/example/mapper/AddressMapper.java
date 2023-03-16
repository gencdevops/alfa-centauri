package org.example.mapper;


import org.example.client.AddressDto;
import org.example.model.Address;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface AddressMapper {
    AddressDto addressToDto(Address address);

    @Mapping(target = "user", ignore = true)
    Address dtoToAddress(AddressDto addressDto);
}

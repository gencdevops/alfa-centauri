package org.example.mapper;

import org.example.client.UserDto;
import org.example.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public abstract class UserMapper {

    protected UserMapper(){
        this.addressMapper = Mappers.getMapper(AddressMapper.class);
    }

    protected AddressMapper addressMapper;

    @Mapping(target = "addressDto", expression = "java(addressMapper.addressToDto(user.getAddress()))")
    public abstract UserDto userToDto(User user);

}

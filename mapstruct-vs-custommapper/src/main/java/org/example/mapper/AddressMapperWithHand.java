package org.example.mapper;

import org.example.client.AddressDto;
import org.example.model.Address;

public class AddressMapperWithHand {

    public AddressDto addresstoAddressDto(Address address){
        if (address == null) {
            return null;
        } else {
            AddressDto.AddressDtoBuilder addressDto = AddressDto.builder();
            addressDto.addressId(address.getAddressId());
            addressDto.address(address.getAddress());
            addressDto.city(address.getCity());
            addressDto.country(address.getCountry());
            addressDto.postCode(address.getPostCode());
            return addressDto.build();
        }
    }
}

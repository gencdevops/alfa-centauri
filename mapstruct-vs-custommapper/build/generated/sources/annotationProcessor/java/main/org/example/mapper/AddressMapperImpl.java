package org.example.mapper;

import javax.annotation.processing.Generated;
import org.example.client.AddressDto;
import org.example.client.AddressDto.AddressDtoBuilder;
import org.example.model.Address;
import org.example.model.Address.AddressBuilder;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-03-16T16:17:21+0300",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.4.jar, environment: Java 17.0.4.1 (Amazon.com Inc.)"
)
public class AddressMapperImpl implements AddressMapper {

    @Override
    public AddressDto addressToDto(Address address) {
        if ( address == null ) {
            return null;
        }

        AddressDtoBuilder addressDto = AddressDto.builder();

        addressDto.addressId( address.getAddressId() );
        addressDto.address( address.getAddress() );
        addressDto.city( address.getCity() );
        addressDto.country( address.getCountry() );
        addressDto.postCode( address.getPostCode() );

        return addressDto.build();
    }

    @Override
    public Address dtoToAddress(AddressDto addressDto) {
        if ( addressDto == null ) {
            return null;
        }

        AddressBuilder address = Address.builder();

        address.addressId( addressDto.getAddressId() );
        address.address( addressDto.getAddress() );
        address.city( addressDto.getCity() );
        address.country( addressDto.getCountry() );
        address.postCode( addressDto.getPostCode() );

        return address.build();
    }
}

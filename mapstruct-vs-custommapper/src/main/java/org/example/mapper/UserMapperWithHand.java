package org.example.mapper;

import org.example.client.AddressDto;
import org.example.client.UserDto;
import org.example.model.User;
import org.mapstruct.factory.Mappers;

public class UserMapperWithHand {

    public UserMapperWithHand(){
        this.addressMapperWithHand = new AddressMapperWithHand();
    }
    private AddressMapperWithHand addressMapperWithHand;

    public UserDto userToDtoWithGetterSetter(User user){
        if (user == null) {
            return null;
        } else {
            return new UserDto(
                    user.getName(),
                    user.getSurname(),
                    user.getEmail(),
                    user.getSalary(),
                    user.getTitle(),
                    user.getGrade(),
                    user.getRole(),
                    user.getDepartment(),
                    addressMapperWithHand.addresstoAddressDto(user.getAddress()));
        }
    }
}

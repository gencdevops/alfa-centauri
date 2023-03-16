package org.example.service;


import org.example.Main;
import org.example.client.UserDto;
import org.example.mapper.UserMapper;
import org.example.mapper.UserMapperWithHand;
import org.example.model.Address;
import org.example.model.User;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserService {

    private final UserMapper userMapperWithStruct;
    private final UserMapperWithHand userMapperWithHand;

    public UserService(){
        this.userMapperWithStruct = Mappers.getMapper(UserMapper.class);
        this.userMapperWithHand = new UserMapperWithHand();

        fillList();
    }

    private void fillList(){
        for (int i = 0; i < 100_000; i++) {
            Address address = Address.builder()
                    .addressId(UUID.randomUUID().toString())
                    .address(UUID.randomUUID().toString())
                    .city(UUID.randomUUID().toString())
                    .country(UUID.randomUUID().toString())
                    .postCode(UUID.randomUUID().toString())
                    .build();


            User user = User.builder()
                    .employeeId(UUID.randomUUID().toString())
                    .name(String.valueOf(this.userList.size()))
                    .surname(String.valueOf(this.userList.size()))
                    .grade(String.valueOf(this.userList.size()))
                    .title(String.valueOf(this.userList.size()))
                    .department(String.valueOf(this.userList.size()))
                    .startDate(LocalDate.now())
                    .birthDate(LocalDate.now().minus(20, ChronoUnit.YEARS))
                    .email(String.valueOf(this.userList.size()))
                    .role(String.valueOf(this.userList.size()))
                    .salary(this.userList.size())
                    .tcNo(String.valueOf(this.userList.size()))
                    .address(address)
                    .build();

            address.setUser(user);

            this.userList.add(user);
        }
    }

    private List<User> userList = new ArrayList<>();

    public List<UserDto> getAllUsers(){
        return userList.stream().map(userMapperWithStruct::userToDto).toList();
    }

    public List<UserDto> getAllUsersWithPureHand(){
        return userList.stream().map(userMapperWithHand::userToDtoWithGetterSetter).toList();
    }
}

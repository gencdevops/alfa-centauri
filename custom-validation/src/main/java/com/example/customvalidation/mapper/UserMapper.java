package com.example.customvalidation.mapper;

import com.example.customvalidation.dto.request.UserRequestDto;
import com.example.customvalidation.dto.response.UserResponseDto;
import com.example.customvalidation.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserResponseDto toResponseDto(User user){
        return new UserResponseDto(
                user.getName(),
                user.getTckn(),
                user.getCity()
        );
    }

    public User toEntity(UserRequestDto userRequestDto){
        return userRequestDto == null ? null : new User(
                userRequestDto.name(),
                userRequestDto.tckn(),
                userRequestDto.city()
        );
    }
}

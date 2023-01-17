package com.example.customvalidationwithoutthrow.mapper;

import com.example.customvalidationwithoutthrow.dto.request.UserRequestDto;
import com.example.customvalidationwithoutthrow.dto.response.UserResponseDto;
import com.example.customvalidationwithoutthrow.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserResponseDto toResponseDto(User user){
        return new UserResponseDto(
                user.getName(),
                user.getTckn()
        );
    }

    public User toEntity(UserRequestDto userRequestDto){
        return userRequestDto == null ? null : new User(
                userRequestDto.name(),
                userRequestDto.tckn()
        );
    }
}

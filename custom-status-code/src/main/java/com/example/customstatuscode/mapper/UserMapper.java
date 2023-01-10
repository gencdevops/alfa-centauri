package com.example.customstatuscode.mapper;

import com.example.customstatuscode.dto.UserResponseDto;
import com.example.customstatuscode.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserResponseDto toResponseDto(User user){
        return new UserResponseDto(user.getUserId());
    }
}

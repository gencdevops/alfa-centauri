package com.example.customstatuscode.service.abstracts;


import com.example.customstatuscode.dto.UserResponseDto;

public interface UserService {
    UserResponseDto findById(Long userId);
}

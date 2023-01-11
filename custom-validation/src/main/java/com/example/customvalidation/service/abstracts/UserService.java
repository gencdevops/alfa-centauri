package com.example.customvalidation.service.abstracts;

import com.example.customvalidation.dto.request.UserRequestDto;
import com.example.customvalidation.dto.response.UserResponseDto;

public interface UserService {
    UserResponseDto save(UserRequestDto userRequestDto);
}

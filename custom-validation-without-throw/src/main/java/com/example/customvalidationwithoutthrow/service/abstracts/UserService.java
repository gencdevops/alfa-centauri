package com.example.customvalidationwithoutthrow.service.abstracts;

import com.example.customvalidationwithoutthrow.dto.request.UserRequestDto;
import com.example.customvalidationwithoutthrow.dto.response.UserResponseDto;

public interface UserService {
    UserResponseDto save(UserRequestDto userRequestDto);
}

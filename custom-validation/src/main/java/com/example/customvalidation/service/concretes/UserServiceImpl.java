package com.example.customvalidation.service.concretes;

import com.example.customvalidation.dto.request.UserRequestDto;
import com.example.customvalidation.dto.response.UserResponseDto;
import com.example.customvalidation.exception.exceptions.UserAlreadyExistException;
import com.example.customvalidation.mapper.UserMapper;
import com.example.customvalidation.repository.UserRepository;
import com.example.customvalidation.service.abstracts.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponseDto save(UserRequestDto userRequestDto) {
        return userRepository.save(userMapper.toEntity(userRequestDto)).map(userMapper::toResponseDto)
                .orElseThrow(
                        () -> new UserAlreadyExistException("Bu tckn ile kayıtlı kullanıcı zaten var.")
                );
    }
}

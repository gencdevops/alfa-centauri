package com.example.customvalidationwithoutthrow.service.concretes;

import com.example.customvalidationwithoutthrow.dto.request.UserRequestDto;
import com.example.customvalidationwithoutthrow.dto.response.UserResponseDto;
import com.example.customvalidationwithoutthrow.exception.exceptions.UserAlreadyExistException;
import com.example.customvalidationwithoutthrow.mapper.UserMapper;
import com.example.customvalidationwithoutthrow.repository.UserRepository;
import com.example.customvalidationwithoutthrow.service.abstracts.UserService;
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

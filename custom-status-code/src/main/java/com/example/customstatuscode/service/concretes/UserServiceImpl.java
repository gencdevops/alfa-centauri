package com.example.customstatuscode.service.concretes;

import com.example.customstatuscode.dto.UserResponseDto;
import com.example.customstatuscode.exception.UserNotFoundException;
import com.example.customstatuscode.mapper.UserMapper;
import com.example.customstatuscode.repository.UserRepository;
import com.example.customstatuscode.service.abstracts.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @Override
    public UserResponseDto findById(Long userId) {
        return userRepository.findById(userId).map(userMapper::toResponseDto)
                .orElseThrow(
                        () -> new UserNotFoundException("User not found")
                );
    }
}

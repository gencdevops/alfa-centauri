package com.example.customstatuscode.repository;

import com.example.customstatuscode.model.User;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public class UserRepository {
    public Optional<User> findById(Long userId){
        return userId == 1L ? Optional.of(new User(userId)) : Optional.empty();
    }
}

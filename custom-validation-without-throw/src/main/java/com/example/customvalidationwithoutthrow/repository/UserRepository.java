package com.example.customvalidationwithoutthrow.repository;

import com.example.customvalidationwithoutthrow.model.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {
    private List<User> users = new ArrayList<>();

    public Optional<User> save(User user){
        if(!users.contains(user)){
            users.add(user);
            return Optional.ofNullable(user);
        }
        return Optional.empty();
    }
}

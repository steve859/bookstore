package com.bookstore.service;

import com.bookstore.entity.User;
import com.bookstore.repository.UserRepository;
import com.bookstore.request.UserCreationRequest;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User createUser(UserCreationRequest request){
        if(userRepository.existsByUsername(request.getUsername())){

        }
    }

}

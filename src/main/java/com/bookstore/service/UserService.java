package com.bookstore.service;

import com.bookstore.entity.User;
import com.bookstore.exception.ErrorCode;
import com.bookstore.mapper.UserMapper;
import com.bookstore.repository.UserRepository;
import com.bookstore.request.UserCreationRequest;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;

    public User createUser(UserCreationRequest request){
        if(userRepository.existsByUsername(request.getUsername())){
            throw new RuntimeException(ErrorCode.USER_ALREADY_EXISTS.getMessage());
        }
        User user = userMapper.toUser(request);
        return userRepository.save(user);
    }

}

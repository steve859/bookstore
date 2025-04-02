package com.bookstore.service;

import com.bookstore.dto.request.ApiResponse;
import com.bookstore.dto.request.UserUpdateRequest;
import com.bookstore.dto.response.UserResponse;
import com.bookstore.entity.User;
import com.bookstore.exception.ErrorCode;
import com.bookstore.mapper.UserMapper;
import com.bookstore.repository.UserRepository;
import com.bookstore.dto.request.UserCreationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public ApiResponse<List<User>> getUsers( ){
        ApiResponse<List<User>> apiResponse = new ApiResponse<>();
        List<User> users = userRepository.findAll();
        apiResponse.setResult(users);
        return apiResponse;
    }

    public ApiResponse<User> getUser(String userId){
        ApiResponse<User> apiResponse = new ApiResponse<>();
        User user = userRepository.findById(userId).orElse(null);
        apiResponse.setResult(user);
        return apiResponse;
    }

    public  UserResponse updateUser(String userId, UserUpdateRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        userMapper.updateUser(user, request);
        return userMapper.toUserResponse(userRepository.save(user));
    }

}

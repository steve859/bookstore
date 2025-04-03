package com.bookstore.mapper;

import com.bookstore.dto.request.ApiResponse;
import com.bookstore.dto.response.UserResponse;
import com.bookstore.entity.User;
import com.bookstore.dto.request.UserCreationRequest;
import com.bookstore.dto.request.UserUpdateRequest;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

    @Component
    @Mapper(componentModel = "spring")
    public interface UserMapper {
        User toUser(UserCreationRequest request);
        User toUser(UserUpdateRequest request);
        void updateUser(@MappingTarget User user, UserUpdateRequest request);
        @Mapping(source = "id", target = "Id")
        UserResponse toUserResponse(User user);
    }

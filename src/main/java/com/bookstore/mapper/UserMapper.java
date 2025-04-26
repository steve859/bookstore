package com.bookstore.mapper;

import com.bookstore.dto.request.ApiResponse;
import com.bookstore.dto.response.UserResponse;
import com.bookstore.entity.Users;
import com.bookstore.dto.request.UserCreationRequest;
import com.bookstore.dto.request.UserUpdateRequest;
import org.mapstruct.*;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
public interface UserMapper {
    Users toUser(UserCreationRequest request);

    UserResponse toUserResponse(Users user);

    @Mapping(target = "roles", ignore = true)
    void updateUser(@MappingTarget Users user, UserUpdateRequest request);
}
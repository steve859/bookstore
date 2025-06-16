package com.bookstore.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.bookstore.dto.request.UserCreationRequest;
import com.bookstore.dto.request.UserUpdateRequest;
import com.bookstore.dto.response.UserResponse;
import com.bookstore.entity.Users;

@Mapper(componentModel = "spring", uses = RoleMapper.class)
public interface UserMapper {
    Users toUser(UserCreationRequest request);

    @Mapping(target = "debtAmount", source = "debtAmount")
    UserResponse toUserResponse(Users user);

    @Mapping(target = "roles", ignore = true)
    void updateUser(@MappingTarget Users user, UserUpdateRequest request);
}
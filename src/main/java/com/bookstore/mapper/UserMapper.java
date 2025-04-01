package com.bookstore.mapper;

import com.bookstore.dto.response.UserResponse;
import com.bookstore.entity.User;
import com.bookstore.dto.request.UserCreationRequest;
import com.bookstore.dto.request.UserUpdateRequest;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.stereotype.Component;

    @Component
    @Mapper(componentModel = "spring")
    public interface UserMapper {
        User toUser(UserCreationRequest request);
        User toUser(UserUpdateRequest request);
        @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
        void updateUserFromRequest(UserUpdateRequest request, @MappingTarget User user);
        UserResponse toUserResponse(User user);
    }

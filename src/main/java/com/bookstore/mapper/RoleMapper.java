package com.bookstore.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.bookstore.dto.request.RoleRequest;
import com.bookstore.dto.response.RoleResponse;
import com.bookstore.entity.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest roleRequest);
    RoleResponse toRoleResponse(Role role);
}

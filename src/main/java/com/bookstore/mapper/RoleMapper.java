package com.bookstore.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.bookstore.dto.request.RoleRequest;
import com.bookstore.dto.response.RoleResponse;
import com.bookstore.entity.Roles;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Roles toRole(RoleRequest roleRequest);

    RoleResponse toRoleResponse(Roles role);
}

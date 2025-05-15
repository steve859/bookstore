package com.bookstore.mapper;

import java.util.List;
import java.util.Set;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.bookstore.dto.request.RoleRequest;
import com.bookstore.dto.response.RoleResponse;
import com.bookstore.entity.Roles;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    @Mapping(target = "permissions", ignore = true)
    Roles toRole(RoleRequest roleRequest);

    @Mapping(source = "permissions", target = "permissions")
    RoleResponse toRoleResponse(Roles role);

    List<RoleResponse> toRoleResponseList(Set<Roles> roles);
}

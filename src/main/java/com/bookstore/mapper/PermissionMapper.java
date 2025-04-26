package com.bookstore.mapper;

import org.mapstruct.Mapper;

import com.bookstore.dto.request.PermissionRequest;
import com.bookstore.dto.response.PermissionResponse;
import com.bookstore.entity.Permissions;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permissions toPermission(PermissionRequest request);

    PermissionResponse toPermissionResponse(Permissions permission);

}

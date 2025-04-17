package com.bookstore.mapper;
import org.mapstruct.Mapper;

import com.bookstore.dto.request.PermissionRequest;
import com.bookstore.dto.response.PermissionResponse;
import com.bookstore.entity.Permission;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);
    PermissionResponse toPermissionResponse(Permission permission);
    
}

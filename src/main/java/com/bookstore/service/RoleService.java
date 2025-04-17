package com.bookstore.service;

import java.security.Permission;
import java.util.HashSet;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bookstore.dto.request.RoleRequest;
import com.bookstore.dto.response.RoleResponse;
import com.bookstore.mapper.RoleMapper;
import com.bookstore.repository.PermissionRepository;
import com.bookstore.repository.RoleRepository;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import lombok.AccessLevel;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RoleService {
    RoleRepository roleRepository;
    RoleMapper roleMapper;
    PermissionRepository permissionRepository;
    public RoleResponse create(RoleRequest request){
        var role = roleMapper.toRole(request);
        var permissions = permissionRepository.findAllById(request.getPermissions());
        role.setPermissions(new HashSet<>(permissions));
        role = roleRepository.save(role);
        return roleMapper.toRoleResponse(role);
    }

    public List<RoleResponse> getAll(){
        return roleRepository.findAll()
        .stream().map(roleMapper::toRoleResponse)
        .toList();
    }
    public void delete(String role){
        roleRepository.deleteById(role);
    }
}

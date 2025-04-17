package com.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookstore.entity.Permission;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, String>{
    
}

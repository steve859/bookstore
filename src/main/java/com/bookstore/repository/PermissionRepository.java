package com.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookstore.entity.Permissions;

@Repository
public interface PermissionRepository extends JpaRepository<Permissions, String> {

}

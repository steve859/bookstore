package com.bookstore.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookstore.entity.Roles;

@Repository
public interface RoleRepository extends JpaRepository<Roles, String> {
    Optional<Roles> findByName(String name);
}

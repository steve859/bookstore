package com.bookstore.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookstore.entity.Users;

@Repository
public interface UserRepository extends JpaRepository<Users, String> {
    boolean existsByUsername(String username);

    Users findByPhone(String phone);

    Optional<Users> findByUsername(String username);
}

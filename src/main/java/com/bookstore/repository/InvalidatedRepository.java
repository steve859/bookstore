package com.bookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookstore.entity.InvalidatedToken;

public interface InvalidatedRepository extends JpaRepository<InvalidatedToken, String> {
    
}

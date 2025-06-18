package com.bookstore.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookstore.entity.Parameters;

public interface ParameterRepository extends JpaRepository<Parameters, String> {
    Optional<Parameters> findByParamName(String name);
}

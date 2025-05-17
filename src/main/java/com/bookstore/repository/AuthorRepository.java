package com.bookstore.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookstore.entity.Authors;

@Repository
public interface AuthorRepository extends JpaRepository<Authors, Integer> {
    boolean existsByAuthorName(String authorName);

    Optional<Authors> findByAuthorName(String authorName);
}

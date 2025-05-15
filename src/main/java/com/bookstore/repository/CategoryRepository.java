package com.bookstore.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookstore.entity.Categories;

@Repository
public interface CategoryRepository extends JpaRepository<Categories, Integer> {
    boolean existsByCategoryName(String categoryName);

    Optional<Categories> findByCategoryName(String categoryName);
}

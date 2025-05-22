package com.bookstore.repository;

import com.bookstore.entity.Books;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Books, Integer> {
    List<Books> findByName(String name);
}

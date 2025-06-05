package com.bookstore.repository;

import com.bookstore.entity.Books;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Books, Integer> {
    @EntityGraph(attributePaths = {"authors", "categories"})
    @Query("SELECT b FROM Books b WHERE b.name = :name")
    List<Books> findByName(@Param("name") String name);
}

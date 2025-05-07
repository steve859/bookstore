package com.bookstore.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "books")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Books {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    Integer bookId;

    @Column(name = "book_name")
    String name;

    @Column(name = "published_year")
    Integer publishedYear;

    @Column(name = "selling_price")
    BigDecimal sellingPrice;

    @Column(name = "quantity")
    int quantity;

    @ManyToMany
    @JoinTable(name = "books_categories", joinColumns = @JoinColumn(name = "book_id"), inverseJoinColumns = @JoinColumn(name = "category_id"))
    Set<Categories> categories = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "authors_books", joinColumns = @JoinColumn(name = "book_id"), inverseJoinColumns = @JoinColumn(name = "author_id"))
    Set<Authors> authors = new HashSet<>();
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    Set<BooksImportReceipts> importDetails = new HashSet<>();

}

package com.bookstore.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "books")
public class Books {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "book_id")
    String id;

    @Column(name = "book_name")
    String name;

    @Column(name = "author_id")
    Integer authorId;

    @Column(name = "published_year")
    Integer published_year;

    @Column(name = "selling_price")
    BigDecimal selling_price;

    @Column(name = "quantity")
    int quantity;
}

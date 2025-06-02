package com.bookstore.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.util.ArrayList;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "books_invoices", uniqueConstraints = @UniqueConstraint(columnNames = {"book_id", "invoice_id"}))
public class BooksInvoices {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @ManyToOne
    @JoinColumn(name = "invoice_id")
    @JsonBackReference
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    Invoices invoice;

    @ManyToOne
    @JoinColumn(name = "book_id")
    Books book;

    @Column(name = "quantity")
    Integer quantity;

    @Column(name = "sell_price", precision = 10, scale = 2)
    BigDecimal sellPrice;
}

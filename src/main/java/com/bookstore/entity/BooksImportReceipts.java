package com.bookstore.entity;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "books_import_receipts", uniqueConstraints = @UniqueConstraint(columnNames = { "book_id",
        "import_receipt_id" }))
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)

public class BooksImportReceipts {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @ManyToOne
    @JoinColumn(name = "book_id")
    Books book;

    @ManyToOne
    @JoinColumn(name = "import_receipt_id")
    @JsonBackReference
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    ImportReceipts importReceipt;

    @Column(name = "quantity")
    Integer quantity;

    @Column(name = "import_price", precision = 10, scale = 2)
    BigDecimal importPrice;
}

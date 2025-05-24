package com.bookstore.entity;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "books_import_receipts",uniqueConstraints = @UniqueConstraint(columnNames = {"book_id", "import_receipt_id"}))
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

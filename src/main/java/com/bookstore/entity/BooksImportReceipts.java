package com.bookstore.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "books_import_receipts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BooksImportReceipts {

    @EmbeddedId
    BooksImportReceiptsID id;

    @ManyToOne
    @MapsId("bookId")
    @JoinColumn(name = "book_id")
    Books book;

    @ManyToOne
    @MapsId("importReceiptId")
    @JoinColumn(name = "import_receipt_id")
    ImportReceipts importReceipt;

    @Column(name = "quantity")
    Integer quantity;

    @Column(name = "import_price", precision = 10, scale = 2)
    BigDecimal importPrice;
}

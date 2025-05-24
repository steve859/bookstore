package com.bookstore.entity;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BooksImportReceiptsID implements Serializable {

    @Column(name = "book_id")
    Integer bookId;
    @Column(name = "import_receipt_id")
    Integer importReceiptId;
}

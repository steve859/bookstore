package com.bookstore.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

import com.bookstore.entity.BooksImportReceipts;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ImportReceiptResponse {
    Integer importReceiptId;
    String adminId;
    LocalDate importDate;
    BigDecimal totalAmount;
    Set<BookUpdateResponse> bookDetails;
}

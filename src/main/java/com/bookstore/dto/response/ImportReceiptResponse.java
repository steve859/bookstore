package com.bookstore.dto.response;

import com.bookstore.entity.BooksImportReceipts;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

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
//    Set<BooksImportReceipts> bookDetails;
}

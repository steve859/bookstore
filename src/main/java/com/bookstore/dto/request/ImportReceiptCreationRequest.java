package com.bookstore.dto.request;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.bookstore.entity.BooksImportReceipts;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ImportReceiptCreationRequest {
    String adminId;
    List<BookCreationRequest> bookDetails;
}

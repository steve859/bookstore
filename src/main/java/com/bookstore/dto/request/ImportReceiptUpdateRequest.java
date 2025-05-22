package com.bookstore.dto.request;

import com.bookstore.entity.BooksImportReceipts;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImportReceiptUpdateRequest {
    String adminId;
    List<BookUpdateRequest> bookDetails;
}

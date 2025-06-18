package com.bookstore.dto.response;

import com.bookstore.entity.BooksInvoices;
import jakarta.persistence.Column;
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
public class InvoiceResponse {
    Integer invoiceId;
    String userId;
    String adminId;
    LocalDate createAt;
    BigDecimal totalAmount;
    BigDecimal paidAmount;
    BigDecimal debtAmount;
    Set<BookUpdateResponse> bookDetails;
}

package com.bookstore.dto.response;

import jakarta.persistence.Column;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;

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
}

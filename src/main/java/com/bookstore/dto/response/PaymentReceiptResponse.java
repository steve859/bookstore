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
public class PaymentReceiptResponse {
    Integer paymentReceiptId;
    String payerId;
    String adminId;
    BigDecimal totalAmount;
    LocalDate createAt;
}

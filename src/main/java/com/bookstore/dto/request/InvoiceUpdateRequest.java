package com.bookstore.dto.request;

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
public class InvoiceUpdateRequest {
    String userId;
    String adminId;
    BigDecimal paidAmount;
    List<BookDeleteRequest> bookDetails;
}

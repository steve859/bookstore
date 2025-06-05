package com.bookstore.dto.response;

import com.bookstore.entity.MonthlyDebtReportDetails;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MonthlyDebtReportResponse {
    Integer debtReportId;
    String userId;
    BigDecimal openingDebt;
    BigDecimal debtIncrease;
    BigDecimal debtPayment;
    BigDecimal closingDebt;
    LocalDate reportMonth;
//    List<MonthlyDebtReportDetails> details;
}

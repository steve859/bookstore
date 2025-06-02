package com.bookstore.dto.response;

import com.bookstore.entity.MonthlyDebtReports;
import com.bookstore.entity.Users;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MonthlyDebtReportDetailResponse {
    Integer deptReportDetailId;
    MonthlyDebtReports deptReportId;
    String userId;
    LocalDate reportDate;
    BigDecimal amount;
    String type;
}

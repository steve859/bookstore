package com.bookstore.dto.response;

import com.bookstore.entity.MonthlyInventoryReports;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MonthlyInventoryReportDetailResponse {
    Integer inventoryReportDetailId;
    MonthlyInventoryReports inventoryReport;
    String bookId;
    LocalDateTime reportMonth;
    BigDecimal amount;
    String type;
}

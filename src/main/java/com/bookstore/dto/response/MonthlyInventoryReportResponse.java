package com.bookstore.dto.response;

import java.time.LocalDate;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MonthlyInventoryReportResponse {
    Integer inventoryReportId;
    Integer bookId;
    String bookName;
    Integer openingStock;
    Integer stockIncrease;
    Integer stockDecrease;
    Integer closingStock;
    LocalDate reportMonth;
    // List<MonthlyInventoryReportDetails> details;
}

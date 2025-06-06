package com.bookstore.dto.response;

import com.bookstore.entity.MonthlyInventoryReportDetails;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MonthlyInventoryReportResponse {
    Integer inventoryReportId;
    Integer bookId;
    Integer openingStock;
    Integer stockIncrease;
    Integer stockDecrease;
    Integer closingStock;
    LocalDate reportMonth;
//    List<MonthlyInventoryReportDetails> details;
}

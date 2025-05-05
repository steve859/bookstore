package com.bookstore.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "monthly_inventory_reports")
public class MonthlyInventoryReports {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inventory_report_id")
    Integer inventoryReportId;

    @Column(name = "book_id")
    Integer bookId;

    @Column(name = "opening_stock")
    Integer openingStock;

    @Column(name = "stock_increase")
    Integer stockIncrease;

    @Column(name = "stock_decrease")
    Integer stockDecrease;

    @Column(name = "closing_stock")
    Integer closingStock;
}

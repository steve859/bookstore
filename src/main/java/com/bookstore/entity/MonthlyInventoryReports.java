package com.bookstore.entity;

import java.time.LocalDate;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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

    @Column(name = "book_name")
    String bookName;

    @Column(name = "opening_stock")
    Integer openingStock;

    @Column(name = "stock_increase")
    Integer stockIncrease;

    @Column(name = "stock_decrease")
    Integer stockDecrease;

    @Column(name = "closing_stock")
    Integer closingStock;

    @Column(name = "report_month")
    LocalDate reportMonth;

    @OneToMany(mappedBy = "inventoryReport", cascade = CascadeType.ALL)
    List<MonthlyInventoryReportDetails> details;
}

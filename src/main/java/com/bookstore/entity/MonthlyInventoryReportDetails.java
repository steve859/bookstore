package com.bookstore.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "monthly_inventory_report_details")
public class MonthlyInventoryReportDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inventory_report_detail_id")
    Integer inventoryReportDetailId;

    @ManyToOne
    @JoinColumn(name = "inventory_report_id")
    MonthlyInventoryReports inventoryReport;

    @Column(name = "book_id")
    Integer bookId;

    @Column(name = "report_date")
    LocalDate reportDate;

    @Column(name = "amount")
    int amount;

    @Column(name = "type")
    String type;
}

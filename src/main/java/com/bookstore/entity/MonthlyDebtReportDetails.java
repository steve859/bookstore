package com.bookstore.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import jakarta.persistence.*;
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
@Table(name = "monthly_debt_report_details")
public class MonthlyDebtReportDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "debt_report_detail_id")
    Integer deptReportDetailId;

    @ManyToOne
    @JoinColumn(name = "debt_report_id")
    MonthlyDebtReports debtReport;

    @Column(name = "user_id")
    String userId;

    @Column(name = "report_date")
    LocalDate reportDate;

    @Column(name = "amount", precision = 10, scale = 2)
    BigDecimal amount;

    @Column(name = "type")
    String type;
}

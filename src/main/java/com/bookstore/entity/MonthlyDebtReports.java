package com.bookstore.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@Table(name = "monthly_debt_reports")
public class MonthlyDebtReports {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "debt_report_id")
    Integer debtReportId;

    @Column(name = "user_id")
    String userId;

    @Column(name = "opening_debt", precision = 10, scale = 2)
    BigDecimal openingDebt;

    @Column(name = "debt_increase", precision = 10, scale = 2)
    BigDecimal debtIncrease;

    @Column(name = "debt_payment", precision = 10, scale = 2)
    BigDecimal debtPayment;

    @Column(name = "closing_debt", precision = 10, scale = 2)
    BigDecimal closingDebt;
}
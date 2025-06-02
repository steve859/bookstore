package com.bookstore.repository;

import com.bookstore.entity.MonthlyDebtReportDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MonthlyDebtReportDetailRepository extends JpaRepository<MonthlyDebtReportDetails, Integer> {
}

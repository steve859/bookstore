package com.bookstore.repository;

import com.bookstore.entity.MonthlyDebtReports;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface MonthlyDebtReportRepository extends JpaRepository<MonthlyDebtReports, Integer> {
    Optional<MonthlyDebtReports> findByUserIdAndReportMonth(String userId, LocalDate date);
    List<MonthlyDebtReports> findAllByReportMonth(LocalDate date);
}

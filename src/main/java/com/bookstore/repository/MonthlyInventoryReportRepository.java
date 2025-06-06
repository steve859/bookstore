package com.bookstore.repository;

import com.bookstore.entity.MonthlyDebtReports;
import com.bookstore.entity.MonthlyInventoryReports;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface MonthlyInventoryReportRepository extends JpaRepository<MonthlyInventoryReports, Integer> {
    Optional<MonthlyInventoryReports> findByBookIdAndReportMonth(Integer bookId, LocalDate date);
}

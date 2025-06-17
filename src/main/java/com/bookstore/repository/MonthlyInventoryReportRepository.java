package com.bookstore.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.bookstore.entity.MonthlyInventoryReports;

@Repository
public interface MonthlyInventoryReportRepository extends JpaRepository<MonthlyInventoryReports, Integer> {
    Optional<MonthlyInventoryReports> findByBookIdAndReportMonth(Integer bookId, LocalDate date);

    List<MonthlyInventoryReports> findAllByReportMonth(LocalDate date);

    @Query(value = """
              SELECT * FROM monthly_inventory_reports
              WHERE EXTRACT(MONTH FROM report_month) = :month
                AND EXTRACT(YEAR FROM report_month) = :year
            """, nativeQuery = true)
    List<MonthlyInventoryReports> findByReportMonthYear(@Param("year") int year, @Param("month") int month);

    @Query("SELECT r FROM MonthlyInventoryReports r WHERE r.reportMonth >= :start AND r.reportMonth < :end")
    List<MonthlyInventoryReports> findByMonthRange(@Param("start") LocalDate start, @Param("end") LocalDate end);

}

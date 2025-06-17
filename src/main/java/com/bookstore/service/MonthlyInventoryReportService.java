package com.bookstore.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookstore.dto.response.MonthlyInventoryReportResponse;
import com.bookstore.entity.Books;
import com.bookstore.entity.MonthlyInventoryReportDetails;
import com.bookstore.entity.MonthlyInventoryReports;
import com.bookstore.exception.AppException;
import com.bookstore.exception.ErrorCode;
import com.bookstore.mapper.MonthlyInventoryReportMapper;
import com.bookstore.repository.BookRepository;
import com.bookstore.repository.MonthlyInventoryReportRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class MonthlyInventoryReportService {
        @Autowired
        MonthlyInventoryReportRepository monthlyInventoryReportRepository;
        @Autowired
        MonthlyInventoryReportMapper monthlyInventoryReportMapper;
        @Autowired
        private BookRepository bookRepository;

        public MonthlyInventoryReportResponse createMonthlyInventoryReport(Integer bookId, LocalDate reportDate) {
                Books book = bookRepository.findById(bookId).orElse(null);
                MonthlyInventoryReports monthlyInventoryReport = MonthlyInventoryReports.builder()
                                .bookId(Integer.valueOf(bookId))
                                .bookName(book.getName())
                                .reportMonth(reportDate)
                                .openingStock(book.getQuantity())
                                .stockIncrease(0)
                                .stockDecrease(0)
                                .closingStock(book.getQuantity())
                                .details(new ArrayList<>())
                                .build();
                return monthlyInventoryReportMapper
                                .toMonthlyInventoryReportResponse(
                                                monthlyInventoryReportRepository.save(monthlyInventoryReport));
        }

        public List<MonthlyInventoryReportResponse> getMonthlyInventoryReports(LocalDate reportMonth) {
                log.info(">>> year: {}, month: {}", reportMonth.getYear(), reportMonth.getMonthValue());

                LocalDate start = LocalDate.of(reportMonth.getYear(), reportMonth.getMonth(), 1);
                LocalDate end = start.plusMonths(1);
                return monthlyInventoryReportRepository
                                .findByMonthRange(start, end)
                                .stream()
                                .map(monthlyInventoryReportMapper::toMonthlyInventoryReportResponse)
                                .toList();
        }

        public MonthlyInventoryReportResponse updateMonthlyInventoryReport(
                        MonthlyInventoryReportDetails monthlyInventoryReportDetail, int amount, String type) {
                MonthlyInventoryReports monthlyInventoryReport = monthlyInventoryReportRepository
                                .findByBookIdAndReportMonth(monthlyInventoryReportDetail.getBookId(),
                                                monthlyInventoryReportDetail.getReportDate().withDayOfMonth(1))
                                .orElseThrow(() -> new AppException(ErrorCode.MONTHLY_INVENTORY_REPORT_NOT_EXISTED));
                if (type.equals("Import")) {
                        monthlyInventoryReport.setStockIncrease(monthlyInventoryReport.getStockIncrease() + amount);
                } else {
                        monthlyInventoryReport.setStockDecrease(monthlyInventoryReport.getStockDecrease() + amount);
                }
                List<MonthlyInventoryReportDetails> details = monthlyInventoryReport.getDetails();
                details.add(monthlyInventoryReportDetail);
                monthlyInventoryReport.setDetails(details);
                monthlyInventoryReport.setClosingStock(monthlyInventoryReport.getOpeningStock()
                                + monthlyInventoryReport.getStockIncrease()
                                - monthlyInventoryReport.getStockDecrease());
                return monthlyInventoryReportMapper
                                .toMonthlyInventoryReportResponse(
                                                monthlyInventoryReportRepository.save(monthlyInventoryReport));
        }
}

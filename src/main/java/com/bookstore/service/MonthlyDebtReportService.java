package com.bookstore.service;

import com.bookstore.dto.response.InvoiceResponse;
import com.bookstore.dto.response.MonthlyDebtReportResponse;
import com.bookstore.entity.*;
import com.bookstore.exception.AppException;
import com.bookstore.exception.ErrorCode;
import com.bookstore.mapper.MonthlyDebtReportMapper;
import com.bookstore.repository.MonthlyDebtReportRepository;
import com.bookstore.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class MonthlyDebtReportService {
    @Autowired
    MonthlyDebtReportRepository monthlyDebtReportRepository;
    MonthlyDebtReportMapper monthlyDebtReportMapper;
    @Autowired
    private UserRepository userRepository;

    public MonthlyDebtReportResponse createMonthlyDebtReport(String userId, LocalDate reportDate) {
        Users user = userRepository.findById(userId).orElse(null);
        MonthlyDebtReports monthlyDebtReports = MonthlyDebtReports.builder()
                .userId(userId)
                .reportMonth(reportDate)
                .openingDebt(user.getDebtAmount())
                .closingDebt(user.getDebtAmount())
                .details(new ArrayList<>())
                .build();
        return monthlyDebtReportMapper.toMonthlyDebtReportResponse(monthlyDebtReportRepository.save(monthlyDebtReports));
    }

    public MonthlyDebtReportResponse updateMonthlyDebtReport(MonthlyDebtReportDetails monthlyDebtReportDetail, BigDecimal amount, String type) {
        MonthlyDebtReports monthlyDebtReport = monthlyDebtReportRepository.findByUserIdAndMonth(monthlyDebtReportDetail.getUserId(),monthlyDebtReportDetail.getReportDate().withDayOfMonth(1))
                .orElseThrow(() -> new AppException(ErrorCode.MONTHLY_DEBT_REPORT_NOT_EXISTED));
        if(type.equals("Debit")){
            monthlyDebtReport.setDebtIncrease(monthlyDebtReport.getDebtIncrease().add(amount));
        }
        else{
            monthlyDebtReport.setDebtPayment(monthlyDebtReport.getDebtPayment().add(amount));
        }
        List<MonthlyDebtReportDetails> details = monthlyDebtReport.getDetails();
        details.add(monthlyDebtReportDetail);
        monthlyDebtReport.setDetails(details);
        monthlyDebtReport.setClosingDebt(monthlyDebtReport.getOpeningDebt().add(monthlyDebtReport.getDebtIncrease()).subtract(monthlyDebtReport.getDebtPayment()));
        return monthlyDebtReportMapper.toMonthlyDebtReportResponse(monthlyDebtReportRepository.save(monthlyDebtReport));
    }
    public List<MonthlyDebtReportResponse> getMonthlyDebtReports() {
        return monthlyDebtReportRepository.findAll().stream().map(monthlyDebtReportMapper::toMonthlyDebtReportResponse).toList();
    }

    public MonthlyDebtReportResponse getMonthlyDebtReport(Integer debtReportId) {
        return monthlyDebtReportMapper.toMonthlyDebtReportResponse(
                monthlyDebtReportRepository.findById(debtReportId).orElseThrow(() -> new RuntimeException("monthly debt report not found")));
    }
}

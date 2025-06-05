package com.bookstore.service;

import com.bookstore.dto.response.MonthlyDebtReportDetailResponse;
import com.bookstore.entity.MonthlyDebtReportDetails;
import com.bookstore.entity.MonthlyDebtReports;
import com.bookstore.entity.Users;
import com.bookstore.mapper.MonthlyDebtReportDetailMapper;
import com.bookstore.repository.MonthlyDebtReportDetailRepository;
import com.bookstore.repository.MonthlyDebtReportRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class MonthlyDebtReportDetailService {
    @Autowired
    MonthlyDebtReportDetailRepository monthlyDebtReportDetailRepository;
    MonthlyDebtReportDetailMapper monthlyDebtReportDetailMapper;
    private MonthlyDebtReportRepository monthlyDebtReportRepository;
    private MonthlyDebtReportService monthlyDebtReportService;

    public MonthlyDebtReportDetailResponse createMonthlyDebtReportDetail(String userId, BigDecimal amount, String type) {
        MonthlyDebtReportDetails monthlyDebtReportDetail = MonthlyDebtReportDetails.builder()
                .userId(userId)
                .amount(amount)
                .type(type)
                .reportDate(LocalDate.now())
                .build();
        MonthlyDebtReports monthlyDebtReport = monthlyDebtReportRepository.findByUserIdAndReportMonth(userId,monthlyDebtReportDetail.getReportDate().withDayOfMonth(1)).orElse(null);
        if(monthlyDebtReport == null) {
            monthlyDebtReportService.createMonthlyDebtReport(userId,monthlyDebtReportDetail.getReportDate().withDayOfMonth(1));
            monthlyDebtReport = monthlyDebtReportRepository.findByUserIdAndReportMonth(userId,monthlyDebtReportDetail.getReportDate().withDayOfMonth(1)).orElse(null);
        }
        monthlyDebtReportDetail.setDebtReport(monthlyDebtReport);
        monthlyDebtReportDetailRepository.save(monthlyDebtReportDetail);
        monthlyDebtReportService.updateMonthlyDebtReport(monthlyDebtReportDetail,amount,type);
        return monthlyDebtReportDetailMapper.toMonthlyDebtReportDetailResponse(monthlyDebtReportDetail);
    }
}

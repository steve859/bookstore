package com.bookstore.service;

import com.bookstore.dto.response.MonthlyDebtReportDetailResponse;
import com.bookstore.dto.response.MonthlyInventoryReportDetailResponse;
import com.bookstore.entity.MonthlyDebtReportDetails;
import com.bookstore.entity.MonthlyDebtReports;
import com.bookstore.entity.MonthlyInventoryReportDetails;
import com.bookstore.entity.MonthlyInventoryReports;
import com.bookstore.exception.AppException;
import com.bookstore.exception.ErrorCode;
import com.bookstore.mapper.MonthlyDebtReportDetailMapper;
import com.bookstore.mapper.MonthlyInventoryReportDetailMapper;
import com.bookstore.mapper.MonthlyInventoryReportMapper;
import com.bookstore.repository.MonthlyDebtReportDetailRepository;
import com.bookstore.repository.MonthlyDebtReportRepository;
import com.bookstore.repository.MonthlyInventoryReportDetailRepository;
import com.bookstore.repository.MonthlyInventoryReportRepository;
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
public class MonthlyInventoryReportDetailService {
    @Autowired
    MonthlyInventoryReportDetailRepository monthlyInventoryReportDetailRepository;
    @Autowired
    MonthlyInventoryReportDetailMapper monthlyInventoryReportDetailMapper;
    @Autowired
    private MonthlyInventoryReportRepository monthlyInventoryReportRepository;
    @Autowired
    private MonthlyInventoryReportService monthlyInventoryReportService;

    public MonthlyInventoryReportDetailResponse createMonthlyInventoryReportDetail(Integer bookId, int amount, String type) {
        MonthlyInventoryReportDetails monthlyInventoryReportDetail = MonthlyInventoryReportDetails.builder()
                .bookId(bookId)
                .amount(amount)
                .type(type)
                .reportDate(LocalDate.now())
                .build();
        MonthlyInventoryReports monthlyInventoryReport = monthlyInventoryReportRepository.findByBookIdAndReportMonth(bookId,monthlyInventoryReportDetail.getReportDate().withDayOfMonth(1)).orElse(null);
        if(monthlyInventoryReport == null) {
            monthlyInventoryReportService.createMonthlyInventoryReport(bookId,monthlyInventoryReportDetail.getReportDate().withDayOfMonth(1));
            monthlyInventoryReport = monthlyInventoryReportRepository.findByBookIdAndReportMonth(bookId,monthlyInventoryReportDetail.getReportDate().withDayOfMonth(1)).orElse(null);
        }
        monthlyInventoryReportDetail.setInventoryReport(monthlyInventoryReport);
        monthlyInventoryReportDetailRepository.save(monthlyInventoryReportDetail);
        monthlyInventoryReportService.updateMonthlyInventoryReport(monthlyInventoryReportDetail,amount,type);
        return monthlyInventoryReportDetailMapper.toMonthlyInventoryReportDetailResponse(monthlyInventoryReportDetail);
    }

//    public MonthlyInventoryReportDetailResponse updateMonthlyInventoryReportDetail(Integer monthlyInventoryReportDetailId, int amount) {
//        MonthlyInventoryReportDetails monthlyInventoryReportDetail = monthlyInventoryReportDetailRepository.findById(monthlyInventoryReportDetailId).orElseThrow(()->new AppException(ErrorCode.MONTHLY_INVENTORY_REPORT_DETAIL_NOT_EXISTED));
//
//    }
}

package com.bookstore.controller;

import com.bookstore.dto.request.ApiResponse;
import com.bookstore.dto.response.MonthlyDebtReportResponse;
import com.bookstore.service.MonthlyDebtReportService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/monthlyDebtReports")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class MonthlyDebtReportController {
    @Autowired
    private MonthlyDebtReportService monthlyDebtReportService;

    @GetMapping("/{}")
    ApiResponse<List<MonthlyDebtReportResponse>> getMonthlyDebtReports() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        authentication.getAuthorities().forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));
        return ApiResponse.<List<MonthlyDebtReportResponse>>builder().result(monthlyDebtReportService.getMonthlyDebtReports()).build();
    }

    @GetMapping("/{debtReportId}")
    ApiResponse<MonthlyDebtReportResponse> getMonthlyDebtReport(@PathVariable("debtReportId") Integer debtReportId) {
        return ApiResponse.<MonthlyDebtReportResponse>builder().result(monthlyDebtReportService.getMonthlyDebtReport(debtReportId)).build();
    }
}

package com.bookstore.controller;

import com.bookstore.dto.request.ApiResponse;
import com.bookstore.dto.response.MonthlyDebtReportResponse;
import com.bookstore.dto.response.MonthlyInventoryReportResponse;
import com.bookstore.service.MonthlyDebtReportService;
import com.bookstore.service.MonthlyInventoryReportService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/monthlyInventoryReports")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class MonthlyInventoryReportController {
    @Autowired
    private MonthlyInventoryReportService monthlyInventoryReportService;

    @GetMapping
    public ApiResponse<List<MonthlyInventoryReportResponse>> getMonthlyInventoryReports(@RequestParam("year") int year, @RequestParam("month") int month) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        authentication.getAuthorities().forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));
        return ApiResponse.<List<MonthlyInventoryReportResponse>>builder().result(monthlyInventoryReportService.getMonthlyInventoryReports(LocalDate.of(year, month, 1))).build();
    }
}

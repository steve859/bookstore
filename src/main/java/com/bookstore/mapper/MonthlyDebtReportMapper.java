package com.bookstore.mapper;

import com.bookstore.dto.response.MonthlyDebtReportResponse;
import com.bookstore.entity.MonthlyDebtReports;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MonthlyDebtReportMapper {
    MonthlyDebtReportResponse toMonthlyDebtReportResponse(MonthlyDebtReports monthlyDebtReports);
}

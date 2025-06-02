package com.bookstore.mapper;

import com.bookstore.dto.response.MonthlyDebtReportDetailResponse;
import com.bookstore.entity.MonthlyDebtReportDetails;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MonthlyDebtReportDetailMapper {
    MonthlyDebtReportDetailResponse toMonthlyDebtReportDetailResponse(MonthlyDebtReportDetails monthlyDebtReportDetail);
}

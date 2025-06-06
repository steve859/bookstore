package com.bookstore.mapper;

import com.bookstore.dto.response.MonthlyInventoryReportDetailResponse;
import com.bookstore.entity.MonthlyInventoryReportDetails;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MonthlyInventoryReportDetailMapper {
    MonthlyInventoryReportDetailResponse toMonthlyInventoryReportDetailResponse(MonthlyInventoryReportDetails monthlyDebtReportDetail);
}

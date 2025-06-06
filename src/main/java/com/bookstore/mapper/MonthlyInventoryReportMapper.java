package com.bookstore.mapper;

import com.bookstore.dto.response.MonthlyInventoryReportResponse;
import com.bookstore.entity.MonthlyInventoryReportDetails;
import com.bookstore.entity.MonthlyInventoryReports;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MonthlyInventoryReportMapper {
    MonthlyInventoryReportResponse toMonthlyInventoryReportResponse(MonthlyInventoryReports monthlyInventoryReport);
}

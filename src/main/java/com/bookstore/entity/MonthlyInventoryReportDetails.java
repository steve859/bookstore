package com.bookstore.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "monthly_inventory_report_details")
public class MonthlyInventoryReportDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inventory_report_detail_id")
    Integer inventoryReportDetailId;

    @Column(name = "inventory_report_id")
    Integer inventoryReportId;

    @Temporal(TemporalType.DATE)
    @Column(name = "report_month")
    Date reportMonth;
}

package com.bookstore.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "invoices")
public class Invoices {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invoice_id")
    Integer invoiceId;

    @Column(name = "user_id")
    String userId;

    @Column(name = "admin_id")
    String adminId;

    @Column(name = "create_at")
    LocalDate createAt;

    @Column(precision = 10, scale = 2, name = "total_amount")
    Double totalAmount;

    @Column(precision = 10, scale = 2, name = "paid_amount")
    Double paidAmount;

    @Column(precision = 10, scale = 2, name = "debt_amount")
    Double debtAmount;
}

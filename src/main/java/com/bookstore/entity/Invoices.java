package com.bookstore.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
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
    BigDecimal totalAmount;

    @Column(precision = 10, scale = 2, name = "paid_amount")
    BigDecimal paidAmount;

    @Column(precision = 10, scale = 2, name = "debt_amount")
    BigDecimal debtAmount;

    @OneToMany(mappedBy = "invoice", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    Set<BooksInvoices> bookDetails = new HashSet<>();
}

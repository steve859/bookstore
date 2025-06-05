package com.bookstore.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "import_receipts")
@ToString(exclude = {"bookDetails"})
@EqualsAndHashCode(exclude = {"bookDetails"})
@Getter
@Setter

public class ImportReceipts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "import_receipt_id")
    Integer importReceiptId;

    @Column(name = "admin_id")
    String adminId;

    @Column(name = "import_date")
    LocalDate importDate;

    @Column(name = "total_amount", precision = 10, scale = 2)
    BigDecimal totalAmount;

    @OneToMany(mappedBy = "importReceipt", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    Set<BooksImportReceipts> bookDetails = new HashSet<>();
}

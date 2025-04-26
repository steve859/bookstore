package com.bookstore.entity;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "import_receipt_details")
public class ImportReceiptDetails {
    @EmbeddedId
    ImportReceiptDetailsID id;

    @Column(name = "quantity")
    Integer quantity;

    @Column(name = "import_price", precision = 10, scale = 2)
    BigDecimal importPrice;
}

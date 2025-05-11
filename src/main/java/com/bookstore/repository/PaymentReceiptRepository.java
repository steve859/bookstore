package com.bookstore.repository;

import com.bookstore.entity.PaymentReceipts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentReceiptRepository extends JpaRepository<PaymentReceipts, Integer> {
}

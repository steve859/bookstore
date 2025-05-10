package com.bookstore.repository;

import com.bookstore.entity.ImportReceipts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImportReceiptRepository extends JpaRepository<ImportReceipts, Integer> {
}

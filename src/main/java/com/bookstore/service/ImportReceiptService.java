package com.bookstore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookstore.dto.request.ImportReceiptCreationRequest;
import com.bookstore.dto.request.ImportReceiptUpdateRequest;
import com.bookstore.dto.response.ImportReceiptRespones;
import com.bookstore.entity.ImportReceipts;
import com.bookstore.exception.AppException;
import com.bookstore.exception.ErrorCode;
import com.bookstore.mapper.ImportReceiptMapper;
import com.bookstore.repository.ImportReceiptRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ImportReceiptService {
    @Autowired
    ImportReceiptRepository importReceiptRepository;
    ImportReceiptMapper importReceiptMapper;

    public ImportReceiptRespones createImportReceipt(ImportReceiptCreationRequest request) {
        ImportReceipts importReceipt = importReceiptMapper.toImportReceipt(request);

        return importReceiptMapper.toImportReceiptRespones(importReceiptRepository.save(importReceipt));
    }

    public List<ImportReceiptRespones> getImportReceipts() {
        return importReceiptRepository.findAll().stream().map(importReceiptMapper::toImportReceiptRespones).toList();
    }

    public ImportReceiptRespones getImportReceipt(Integer importReceiptId) {
        return importReceiptMapper.toImportReceiptRespones(importReceiptRepository.findById(importReceiptId)
                .orElseThrow(() -> new RuntimeException("Import receipt not found")));
    }

    public ImportReceiptRespones updateImportReceipt(Integer importRequestId, ImportReceiptUpdateRequest request) {
        ImportReceipts importReceipt = importReceiptRepository.findById(importRequestId)
                .orElseThrow(() -> new AppException(ErrorCode.IMPORT_RECEIPT_NOT_EXISTED));
        importReceiptMapper.updateImportReceipts(importReceipt, request);
        return importReceiptMapper.toImportReceiptRespones(importReceiptRepository.save(importReceipt));
    }

    public void deleteImportReceipt(Integer importReceiptId) {
        ImportReceipts importReceipt = importReceiptRepository.findById(importReceiptId).orElse(null);
        if (importReceipt != null) {
            importReceiptRepository.delete(importReceipt);
        }
    }
}

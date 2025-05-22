package com.bookstore.service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.bookstore.dto.request.BookCreationRequest;
import com.bookstore.dto.response.ImportReceiptResponse;
import com.bookstore.entity.BooksImportReceipts;
import com.bookstore.mapper.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookstore.dto.request.ImportReceiptCreationRequest;
import com.bookstore.dto.request.ImportReceiptUpdateRequest;
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
    @Autowired
    private BookService bookService;
    @Autowired
    private BookMapper bookMapper;

    public ImportReceiptResponse createImportReceipt(ImportReceiptCreationRequest request) {
        ImportReceipts importReceipt = importReceiptMapper.toImportReceipt(request);
        Set<BooksImportReceipts> booksImportReceiptsSet = new HashSet<>();
        List<BookCreationRequest> inputBooks = request.getBookDetails();
        int totalQuantity = inputBooks.stream().mapToInt(BookCreationRequest::getQuantity).sum();
        if(totalQuantity<150) {
            throw new IllegalArgumentException("Import quantity must be at least 150 books. Current quantity: " + totalQuantity);
        }
        for(BookCreationRequest inputBook : inputBooks) {
            BooksImportReceipts booksImportReceipt = new BooksImportReceipts();
            booksImportReceipt.setBook(bookMapper.toBook(bookService.createBook(inputBook)));
            booksImportReceipt.setImportReceipt(importReceipt);
            booksImportReceipt.setQuantity(inputBook.getQuantity());
            booksImportReceipt.setImportPrice(inputBook.getImportPrice());
            booksImportReceiptsSet.add(booksImportReceipt);
        }
        importReceipt.setBookDetails(booksImportReceiptsSet);
        return importReceiptMapper.toImportReceiptResponse(importReceiptRepository.save(importReceipt));
    }

    public List<ImportReceiptResponse> getImportReceipts() {
        return importReceiptRepository.findAll().stream().map(importReceiptMapper::toImportReceiptResponse).toList();
    }

    public ImportReceiptResponse getImportReceipt(Integer importReceiptId) {
        return importReceiptMapper.toImportReceiptResponse(importReceiptRepository.findById(importReceiptId)
                .orElseThrow(() -> new RuntimeException("Import receipt not found")));
    }

    public ImportReceiptResponse updateImportReceipt(Integer importRequestId, ImportReceiptUpdateRequest request) {
        ImportReceipts importReceipt = importReceiptRepository.findById(importRequestId)
                .orElseThrow(() -> new AppException(ErrorCode.IMPORT_RECEIPT_NOT_EXISTED));
        importReceiptMapper.updateImportReceipts(importReceipt, request);
        return importReceiptMapper.toImportReceiptResponse(importReceiptRepository.save(importReceipt));
    }

    public void deleteImportReceipt(Integer importReceiptId) {
        ImportReceipts importReceipt = importReceiptRepository.findById(importReceiptId).orElse(null);
        if (importReceipt != null) {
            importReceiptRepository.delete(importReceipt);
        }
    }
}

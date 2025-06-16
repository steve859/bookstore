package com.bookstore.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bookstore.dto.request.BookUpdateRequest;
import com.bookstore.dto.request.ImportReceiptCreationRequest;
import com.bookstore.dto.request.ImportReceiptUpdateRequest;
import com.bookstore.dto.response.ImportReceiptResponse;
import com.bookstore.entity.Books;
import com.bookstore.entity.BooksImportReceipts;
import com.bookstore.entity.ImportReceipts;
import com.bookstore.exception.AppException;
import com.bookstore.exception.ErrorCode;
import com.bookstore.mapper.BookMapper;
import com.bookstore.mapper.ImportReceiptMapper;
import com.bookstore.repository.BookRepository;
import com.bookstore.repository.ImportReceiptRepository;
import com.bookstore.repository.UserRepository;

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
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    UserRepository userRepository;

    @Transactional
    public ImportReceiptResponse createImportReceipt(ImportReceiptCreationRequest request) {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        String adminId;
        if (auth instanceof JwtAuthenticationToken) {
            Jwt jwt = ((JwtAuthenticationToken) auth).getToken();
            adminId = jwt.getClaim("id");
        } else {
            String username = auth.getName();
            adminId = userRepository.findByUsername(username)
                    .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED))
                    .getId();
        }
        List<BookUpdateRequest> inputBooks = request.getBookDetails();
        int totalQuantity = inputBooks.stream().mapToInt(BookUpdateRequest::getQuantity).sum();

        if (totalQuantity < 150) {
            throw new AppException(ErrorCode.INSUFFICIENT_IMPORT_QUANTITY);
        }

        ImportReceipts importReceipt = importReceiptMapper.toImportReceipt(request);
        importReceipt.setImportDate(LocalDate.now());

        Set<BooksImportReceipts> booksImportReceiptsSet = new HashSet<>();
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (BookUpdateRequest inputBookRequest : inputBooks) {
            bookService.updateBook(inputBookRequest.getBookId(), inputBookRequest);
            Books inputBook = bookRepository.findById(inputBookRequest.getBookId()).orElse(null);
            BooksImportReceipts booksImportReceipt = new BooksImportReceipts();
            booksImportReceipt.setBook(inputBook);
            booksImportReceipt.setImportReceipt(importReceipt);
            booksImportReceipt.setQuantity(inputBookRequest.getQuantity());
            booksImportReceipt.setImportPrice(inputBookRequest.getImportPrice());

            BigDecimal lineAmount = inputBookRequest.getImportPrice()
                    .multiply(BigDecimal.valueOf(inputBookRequest.getQuantity()));
            totalAmount = totalAmount.add(lineAmount);

            booksImportReceiptsSet.add(booksImportReceipt);
        }

        importReceipt.setTotalAmount(totalAmount);
        importReceipt.setBookDetails(booksImportReceiptsSet);
        importReceipt.setAdminId(adminId);

        ImportReceipts savedImportReceipt = importReceiptRepository.save(importReceipt);

        return importReceiptMapper.toImportReceiptResponse(savedImportReceipt);
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

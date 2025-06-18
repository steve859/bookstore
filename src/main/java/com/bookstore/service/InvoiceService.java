package com.bookstore.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import com.bookstore.dto.request.BookDeleteRequest;
import com.bookstore.entity.Books;
import com.bookstore.entity.BooksInvoices;
import com.bookstore.entity.Users;
import com.bookstore.mapper.BookMapper;
import com.bookstore.repository.BookRepository;
import com.bookstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.bookstore.dto.request.InvoiceCreationRequest;
import com.bookstore.dto.request.InvoiceUpdateRequest;
import com.bookstore.dto.response.InvoiceResponse;
import com.bookstore.entity.Invoices;
import com.bookstore.exception.AppException;
import com.bookstore.exception.ErrorCode;
import com.bookstore.mapper.InvoiceMapper;
import com.bookstore.repository.InvoiceRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class InvoiceService {
    @Autowired
    InvoiceRepository invoiceRepository;
    InvoiceMapper invoiceMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookMapper bookMapper;
    @Autowired
    private BookService bookService;
    @Autowired
    private MonthlyDebtReportDetailService monthlyDebtReportDetailService;
    @Autowired
    private MonthlyInventoryReportDetailService monthlyInventoryReportDetailService;

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public InvoiceResponse createInvoice(InvoiceCreationRequest request) {
        List<BookDeleteRequest> outputBooks = request.getBookDetails();
        Optional<Users> customerOpt = userRepository.findById(request.getUserId());
        if (customerOpt.isEmpty()) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }
        Users customer = customerOpt.get();
        if (customer.getDebtAmount().compareTo(BigDecimal.valueOf(1000000)) > 0) {
            throw new AppException(ErrorCode.DEBT_AMOUNT_LIMIT_EXCEEDED);
        }
        Invoices inputInvoice = invoiceMapper.toInvoice(request);
        inputInvoice.setUserId(String.valueOf(customer.getId()));

        Set<BooksInvoices> bookDetails = new HashSet<>();
        BigDecimal totalAmount = BigDecimal.ZERO;

        for (BookDeleteRequest outputBook : outputBooks) {
            Books book = bookService.getBook(outputBook.getBookId());
            if (book.getQuantity() - outputBook.getQuantity() < 20) {
                throw new AppException(ErrorCode.BOOK_QUANTITY_UNDER_LIMIT);
            }
            BooksInvoices booksInvoice = new BooksInvoices();
            booksInvoice.setInvoice(inputInvoice);
            booksInvoice.setQuantity(outputBook.getQuantity());
            booksInvoice.setSellPrice(book.getImportPrice().multiply(BigDecimal.valueOf(1.05)));
            totalAmount = totalAmount.add(booksInvoice.getSellPrice().multiply(BigDecimal.valueOf(outputBook.getQuantity())));
            bookDetails.add(booksInvoice);
            booksInvoice.setBook(book);
            monthlyInventoryReportDetailService.createMonthlyInventoryReportDetail(book.getBookId(), outputBook.getQuantity(), "Export");

            book.setQuantity(book.getQuantity() - outputBook.getQuantity());
            bookRepository.save(book);
        }

        inputInvoice.setCreateAt(LocalDate.now());
        inputInvoice.setTotalAmount(totalAmount);
        inputInvoice.setDebtAmount(totalAmount.subtract(inputInvoice.getPaidAmount()));
        inputInvoice.setBookDetails(bookDetails);
        monthlyDebtReportDetailService.createMonthlyDebtReportDetail(inputInvoice.getUserId(), totalAmount, "Debit");
        if (inputInvoice.getPaidAmount().compareTo(BigDecimal.ZERO) > 0) {
            monthlyDebtReportDetailService.createMonthlyDebtReportDetail(inputInvoice.getUserId(), inputInvoice.getPaidAmount(), "Credit");
        }
        if (inputInvoice.getDebtAmount().compareTo(BigDecimal.ZERO) > 0) {
            customer.setDebtAmount(customer.getDebtAmount().add(inputInvoice.getDebtAmount()));
            userRepository.save(customer);
        }
        return invoiceMapper.toInvoiceResponse(invoiceRepository.save(inputInvoice));
    }


    public List<InvoiceResponse> getInvoices() {
        return invoiceRepository.findAll().stream().map(invoiceMapper::toInvoiceResponse).toList();
    }

    public InvoiceResponse getInvoice(Integer invoiceId) {
        return invoiceMapper.toInvoiceResponse(
                invoiceRepository.findById(invoiceId).orElseThrow(() -> new RuntimeException("Invoice not found")));
    }

    @Transactional
    public InvoiceResponse updateInvoice(Integer invoiceId, InvoiceUpdateRequest request) {
        Invoices invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new AppException(ErrorCode.INVOICE_NOT_EXISTED));

        List<BookDeleteRequest> newBooks = request.getBookDetails();
        Optional<Users> customerOpt = userRepository.findById(request.getUserId());
        if (customerOpt.isEmpty()) throw new AppException(ErrorCode.USER_NOT_EXISTED);
        Users customer = customerOpt.get();
        BigDecimal oldDebt = invoice.getDebtAmount() != null ? invoice.getDebtAmount() : BigDecimal.ZERO;
        Map<Integer, BooksInvoices> oldBookDetailMap = invoice.getBookDetails().stream()
                .collect(Collectors.toMap(b -> b.getBook().getBookId(), b -> b));

        Map<Integer, Integer> oldQuantityMap = invoice.getBookDetails().stream()
                .collect(Collectors.toMap(b -> b.getBook().getBookId(), BooksInvoices::getQuantity));

        Set<BooksInvoices> existingBookDetails = invoice.getBookDetails();
        Set<Integer> processedBookIds = new HashSet<>();
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (BookDeleteRequest bookReq : newBooks) {
            int bookId = bookReq.getBookId();
            int newQty = bookReq.getQuantity();
            int oldQty = oldQuantityMap.getOrDefault(bookId, 0);
            int qtyDiff = newQty - oldQty;
            Books book = bookService.getBook(bookId);
            if (book.getQuantity() - qtyDiff < 20) {
                throw new AppException(ErrorCode.BOOK_QUANTITY_UNDER_LIMIT);
            }
            book.setQuantity(book.getQuantity() - qtyDiff);
            bookRepository.save(book);

            BooksInvoices bookInvoice = oldBookDetailMap.get(bookId);
            if (bookInvoice != null) {
                bookInvoice.setQuantity(newQty);
                bookInvoice.setSellPrice(book.getImportPrice().multiply(BigDecimal.valueOf(1.05)));
            } else {

                bookInvoice = new BooksInvoices();
                bookInvoice.setInvoice(invoice);
                bookInvoice.setBook(book);
                bookInvoice.setQuantity(newQty);
                bookInvoice.setSellPrice(book.getImportPrice().multiply(BigDecimal.valueOf(1.05)));
                existingBookDetails.add(bookInvoice);
            }

            totalAmount = totalAmount.add(bookInvoice.getSellPrice().multiply(BigDecimal.valueOf(newQty)));
            processedBookIds.add(bookId);

            if (qtyDiff != 0) {
                monthlyInventoryReportDetailService.createMonthlyInventoryReportDetail(bookId, qtyDiff, "Export");
            }
        }

        Set<Integer> newBookIds = newBooks.stream()
                .map(BookDeleteRequest::getBookId)
                .collect(Collectors.toSet());

        Iterator<BooksInvoices> iterator = existingBookDetails.iterator();
        while (iterator.hasNext()) {
            BooksInvoices oldDetail = iterator.next();
            int bookId = oldDetail.getBook().getBookId();

            if (!newBookIds.contains(bookId)) {
                Books book = oldDetail.getBook();
                book.setQuantity(book.getQuantity() + oldDetail.getQuantity());
                bookRepository.save(book);

                monthlyInventoryReportDetailService.createMonthlyInventoryReportDetail(
                        bookId, -oldDetail.getQuantity(), "Export");
                iterator.remove();
            }
        }

        invoice.setUserId(request.getUserId());
        invoice.setTotalAmount(totalAmount);
        invoice.setPaidAmount(request.getPaidAmount());
        BigDecimal newDebt = totalAmount.subtract(request.getPaidAmount());
        invoice.setDebtAmount(newDebt);
        BigDecimal debtDifference = newDebt.subtract(oldDebt);
        customer.setDebtAmount(customer.getDebtAmount().add(debtDifference));
        userRepository.save(customer);
        monthlyDebtReportDetailService.createMonthlyDebtReportDetail(
                invoice.getUserId(), totalAmount, "Debit");

        if (request.getPaidAmount().compareTo(BigDecimal.ZERO) > 0) {
            monthlyDebtReportDetailService.createMonthlyDebtReportDetail(
                    invoice.getUserId(), request.getPaidAmount(), "Credit");
        }
        return invoiceMapper.toInvoiceResponse(invoiceRepository.save(invoice));
    }



    public void deleteInvoice(Integer invoiceId) {
        Invoices invoice = invoiceRepository.findById(invoiceId).orElse(null);
        if (invoice != null) {
            invoiceRepository.delete(invoice);
        }
    }
}

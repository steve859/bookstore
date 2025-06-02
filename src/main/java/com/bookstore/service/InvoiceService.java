package com.bookstore.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.bookstore.dto.request.BookDeleteRequest;
import com.bookstore.entity.Books;
import com.bookstore.entity.BooksInvoices;
import com.bookstore.entity.Users;
import com.bookstore.mapper.BookMapper;
import com.bookstore.repository.BookRepository;
import com.bookstore.repository.UserRepository;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Transactional
    public InvoiceResponse createInvoice(InvoiceCreationRequest request) {
        List<BookDeleteRequest> outputBooks = request.getBookDetails();
        Users customer = userRepository.findByPhone(request.getPhone());
        if (customer == null){
            customer = Users.builder()
                    .username(request.getUserName())
                    .phone(request.getPhone())
                    .debtAmount(BigDecimal.valueOf(0))
                    .build();
            customer = userRepository.save(customer);
        }
        if(customer.getDebtAmount().compareTo(BigDecimal.valueOf(1000000)) > 0){
            throw new AppException(ErrorCode.DEBT_AMOUNT_LIMIT_EXCEEDED);
        }
        Invoices inputInvoice = invoiceMapper.toInvoice(request);
        Set<BooksInvoices> bookDetails = new HashSet<>();
        BigDecimal totalAmount = BigDecimal.ZERO;
        for(BookDeleteRequest outputBook : outputBooks ){
            Books book = bookService.getBook(outputBook.getBookId());
            if(book.getQuantity()-outputBook.getQuantity() < 20){
                throw new AppException(ErrorCode.BOOK_QUANTITY_UNDER_LIMIT);
            }
            book.setQuantity(book.getQuantity()-outputBook.getQuantity());
            BooksInvoices booksInvoice = new BooksInvoices();
            booksInvoice.setBook(book);
            booksInvoice.setInvoice(inputInvoice);
            booksInvoice.setQuantity(outputBook.getQuantity());
            booksInvoice.setSellPrice(book.getImportPrice().multiply(BigDecimal.valueOf(1.05)));
            totalAmount = totalAmount.add(booksInvoice.getSellPrice().multiply(BigDecimal.valueOf(outputBook.getQuantity())));
            bookDetails.add(booksInvoice);
            bookRepository.save(book);
        }
        inputInvoice.setCreateAt(LocalDate.now());
        inputInvoice.setTotalAmount(totalAmount);
        inputInvoice.setDebtAmount(totalAmount.subtract(inputInvoice.getPaidAmount()));
        inputInvoice.setBookDetails(bookDetails);
        monthlyDebtReportDetailService.createMonthlyDebtReportDetail(inputInvoice.getUserId(), totalAmount, "Debit");
        if(inputInvoice.getPaidAmount().compareTo(BigDecimal.ZERO) > 0){
            monthlyDebtReportDetailService.createMonthlyDebtReportDetail(inputInvoice.getUserId(), totalAmount, "Credit");
        }
        if(inputInvoice.getDebtAmount().compareTo(BigDecimal.ZERO) > 0){
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

    public InvoiceResponse updateInvoice(Integer invoiceId, InvoiceUpdateRequest request) {
        Invoices invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new AppException(ErrorCode.INVOICE_NOT_EXISTED));
        invoiceMapper.updateInvoice(invoice, request);
        return invoiceMapper.toInvoiceResponse(invoiceRepository.save(invoice));
    }

    public void deleteInvoice(Integer invoiceId) {
        Invoices invoice = invoiceRepository.findById(invoiceId).orElse(null);
        if (invoice != null) {
            invoiceRepository.delete(invoice);
        }
    }
}

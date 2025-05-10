package com.bookstore.service;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class InvoiceService {
    @Autowired
    InvoiceRepository invoiceRepository;
    InvoiceMapper invoiceMapper;

    public InvoiceResponse createInvoice(InvoiceCreationRequest request) {
        Invoices invoice = invoiceMapper.toInvoice(request);
        return invoiceMapper.toInvoiceResponse(invoiceRepository.save(invoice));
    }

    public List<InvoiceResponse> getInvoices() {
        return invoiceRepository.findAll().stream().map(invoiceMapper::toInvoiceResponse).toList();
    }

    public InvoiceResponse getInvoice(Integer invoiceId) {
        return invoiceMapper.toInvoiceResponse(
                invoiceRepository.findById(invoiceId).orElseThrow(()->new RuntimeException("Invoice not found")));
    }

    public InvoiceResponse updateInvoice(Integer invoiceId, InvoiceUpdateRequest request) {
        Invoices invoice = invoiceRepository.findById(invoiceId).orElseThrow(()->new AppException(ErrorCode.INVOICE_NOT_EXISTED));
        invoiceMapper.updateInvoice(invoice, request);
        return invoiceMapper.toInvoiceResponse(invoiceRepository.save(invoice));
    }

    public void deleteInvoice(Integer invoiceId) {
        Invoices invoice = invoiceRepository.findById(invoiceId).orElse(null);
        if(invoice != null) {
            invoiceRepository.delete(invoice);
        }
    }
}

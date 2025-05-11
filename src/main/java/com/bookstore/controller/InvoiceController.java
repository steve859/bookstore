package com.bookstore.controller;

import com.bookstore.dto.request.ApiResponse;
import com.bookstore.dto.request.InvoiceCreationRequest;
import com.bookstore.dto.request.InvoiceUpdateRequest;
import com.bookstore.dto.response.InvoiceResponse;
import com.bookstore.entity.Invoices;
import com.bookstore.service.InvoiceService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/invoices")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class InvoiceController {
    @Autowired
    private InvoiceService invoiceService;

    @PostMapping
    ApiResponse<InvoiceResponse> createInvoice(@RequestBody InvoiceCreationRequest request) {
        return ApiResponse.<InvoiceResponse>builder().result(invoiceService.createInvoice(request)).build();
    }

    @GetMapping
    ApiResponse<List<InvoiceResponse>> getInvoices() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        authentication.getAuthorities().forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));
        return ApiResponse.<List<InvoiceResponse>>builder().result(invoiceService.getInvoices()).build();
    }

    @GetMapping("/{invoiceId}")
    ApiResponse<InvoiceResponse> getInvoice(@PathVariable("invoiceId") Integer invoiceId) {
        return ApiResponse.<InvoiceResponse>builder().result(invoiceService.getInvoice(invoiceId)).build();
    }

    @PutMapping("/{invoiceId}")
    ApiResponse<InvoiceResponse> updateInvoice(@PathVariable("invoiceId") Integer invoiceId, @RequestBody InvoiceUpdateRequest request) {
        return ApiResponse.<InvoiceResponse>builder().result(invoiceService.updateInvoice(invoiceId, request)).build();
    }

    @DeleteMapping("/{invoiceId}")
    ApiResponse<String> deleteInvoice(@PathVariable("invoiceId") Integer invoiceId) {
        invoiceService.deleteInvoice(invoiceId);
        return ApiResponse.<String>builder().result("Invoice has been deleted").build();
    }
}

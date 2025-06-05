package com.bookstore.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import com.bookstore.dto.request.ApiResponse;
import com.bookstore.dto.request.PaymentReceiptCreationRequest;
import com.bookstore.dto.request.PaymentReceiptUpdateRequest;
import com.bookstore.dto.response.PaymentReceiptResponse;
import com.bookstore.service.PaymentReceiptService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/paymentReceipts")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PaymentReceiptController {
    @Autowired
    private PaymentReceiptService paymentReceiptService;

    @PostMapping
    ApiResponse<PaymentReceiptResponse> createPaymentReceipt(@RequestBody PaymentReceiptCreationRequest request) {
        return ApiResponse.<PaymentReceiptResponse>builder().result(paymentReceiptService.createPaymentReceipt(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<PaymentReceiptResponse>> getPaymentReceipts() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        authentication.getAuthorities().forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));
        return ApiResponse.<List<PaymentReceiptResponse>>builder().result(paymentReceiptService.getPaymentReceipts())
                .build();
    }

    @GetMapping("/{paymentReceiptId}")
    ApiResponse<PaymentReceiptResponse> getPaymentReceipt(@PathVariable("paymentReceiptId") Integer paymentReceiptId) {
        return ApiResponse.<PaymentReceiptResponse>builder()
                .result(paymentReceiptService.getPaymentReceipt(paymentReceiptId)).build();
    }

    @PutMapping("/{paymentReceiptId}")
    ApiResponse<PaymentReceiptResponse> updatePaymentReceipt(@PathVariable("paymentReceiptId") Integer paymentReceiptId,
            PaymentReceiptUpdateRequest request) {
        return ApiResponse.<PaymentReceiptResponse>builder()
                .result(paymentReceiptService.updatePaymentReceipt(paymentReceiptId, request)).build();
    }

    @DeleteMapping("/{paymentReceiptId}")
    ApiResponse<String> deletePaymentReceipt(@PathVariable("paymentReceiptId") Integer paymentReceiptId) {
        paymentReceiptService.deletePaymentReceipt(paymentReceiptId);
        return ApiResponse.<String>builder().result("PaymentReceipt has been deleted").build();
    }
}

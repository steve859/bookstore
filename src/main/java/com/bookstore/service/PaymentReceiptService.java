package com.bookstore.service;

import com.bookstore.dto.request.PaymentReceiptCreationRequest;
import com.bookstore.dto.request.PaymentReceiptUpdateRequest;
import com.bookstore.dto.response.PaymentReceiptResponse;
import com.bookstore.entity.PaymentReceipts;
import com.bookstore.exception.AppException;
import com.bookstore.exception.ErrorCode;
import com.bookstore.mapper.PaymentReceiptMapper;
import com.bookstore.repository.PaymentReceiptRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PaymentReceiptService {
    @Autowired
    PaymentReceiptRepository paymentReceiptRepository;
    PaymentReceiptMapper paymentReceiptMapper;
    public PaymentReceiptResponse createPaymentReceipt(PaymentReceiptCreationRequest request) {
        PaymentReceipts paymentReceipt = paymentReceiptMapper.toPaymentReceipts(request);
        return paymentReceiptMapper.toPaymentReceiptResponse(paymentReceiptRepository.save(paymentReceipt));
    }

    public List<PaymentReceiptResponse> getPaymentReceipts() {
        return paymentReceiptRepository.findAll().stream().map(paymentReceiptMapper::toPaymentReceiptResponse).toList();
    }

    public PaymentReceiptResponse getPaymentReceipt(Integer paymentReceiptId) {
        return paymentReceiptMapper.toPaymentReceiptResponse(
                paymentReceiptRepository.findById(paymentReceiptId).orElseThrow(()->new RuntimeException("Payment receipt not found")));
    }

    public PaymentReceiptResponse updatePaymentReceipt(Integer paymentReceiptId, PaymentReceiptUpdateRequest request) {
        PaymentReceipts paymentReceipt = paymentReceiptRepository.findById(paymentReceiptId).orElseThrow(()->new AppException(ErrorCode.PAYMENT_RECEIPT_NOT_EXISTED));
        paymentReceiptMapper.updatePaymentReceipt(paymentReceipt, request);
        return paymentReceiptMapper.toPaymentReceiptResponse(paymentReceiptRepository.save(paymentReceipt));
    }

    public void deletePaymentReceipt(Integer paymentReceiptId) {
        PaymentReceipts paymentReceipt = paymentReceiptRepository.findById(paymentReceiptId).orElse(null);
        if(paymentReceipt != null) {
            paymentReceiptRepository.delete(paymentReceipt);
        }
    }
}

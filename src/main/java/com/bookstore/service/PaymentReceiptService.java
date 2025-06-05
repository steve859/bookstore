package com.bookstore.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.bookstore.entity.Users;
import com.bookstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PaymentReceiptService {
    @Autowired
    PaymentReceiptRepository paymentReceiptRepository;
    PaymentReceiptMapper paymentReceiptMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MonthlyDebtReportDetailService monthlyDebtReportDetailService;
    @Autowired
    private MonthlyInventoryReportDetailService monthlyInventoryReportDetailService;

    public PaymentReceiptResponse createPaymentReceipt(PaymentReceiptCreationRequest request) {
        PaymentReceipts paymentReceipt = paymentReceiptMapper.toPaymentReceipts(request);
        paymentReceipt.setCreateAt(LocalDate.now());
        Users user = userRepository.findById(paymentReceipt.getPayerId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        monthlyDebtReportDetailService.createMonthlyDebtReportDetail(String.valueOf(user.getId()),paymentReceipt.getTotalAmount(),"Credit");
        user.setDebtAmount(user.getDebtAmount().subtract(paymentReceipt.getTotalAmount()));
        userRepository.save(user);

        return paymentReceiptMapper.toPaymentReceiptResponse(paymentReceiptRepository.save(paymentReceipt));
    }

    public List<PaymentReceiptResponse> getPaymentReceipts() {
        return paymentReceiptRepository.findAll().stream().map(paymentReceiptMapper::toPaymentReceiptResponse).toList();
    }

    public PaymentReceiptResponse getPaymentReceipt(Integer paymentReceiptId) {
        return paymentReceiptMapper.toPaymentReceiptResponse(
                paymentReceiptRepository.findById(paymentReceiptId)
                        .orElseThrow(() -> new RuntimeException("Payment receipt not found")));
    }

    public PaymentReceiptResponse updatePaymentReceipt(Integer paymentReceiptId, PaymentReceiptUpdateRequest request) {
        PaymentReceipts paymentReceipt = paymentReceiptRepository.findById(paymentReceiptId)
                .orElseThrow(() -> new AppException(ErrorCode.PAYMENT_RECEIPT_NOT_EXISTED));
        paymentReceiptMapper.updatePaymentReceipt(paymentReceipt, request);
        return paymentReceiptMapper.toPaymentReceiptResponse(paymentReceiptRepository.save(paymentReceipt));
    }

    public void deletePaymentReceipt(Integer paymentReceiptId) {
        PaymentReceipts paymentReceipt = paymentReceiptRepository.findById(paymentReceiptId).orElse(null);
        if (paymentReceipt != null) {
            paymentReceiptRepository.delete(paymentReceipt);
        }
    }
}

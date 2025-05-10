package com.bookstore.mapper;

import com.bookstore.dto.request.PaymentReceiptCreationRequest;
import com.bookstore.dto.request.PaymentReceiptUpdateRequest;
import com.bookstore.dto.response.PaymentReceiptResponse;
import com.bookstore.entity.PaymentReceipts;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PaymentReceiptMapper {
    PaymentReceipts toPaymentReceipts(PaymentReceiptCreationRequest request);
    PaymentReceiptResponse toPaymentReceiptResponse(PaymentReceipts paymentReceipt);
    void updatePaymentReceipt(@MappingTarget PaymentReceipts paymentReceipt, PaymentReceiptUpdateRequest request);
}

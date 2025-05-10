package com.bookstore.mapper;

import com.bookstore.dto.request.InvoiceCreationRequest;
import com.bookstore.dto.request.InvoiceUpdateRequest;
import com.bookstore.dto.response.InvoiceResponse;
import com.bookstore.entity.Invoices;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface InvoiceMapper {
    Invoices toInvoice(InvoiceCreationRequest request);
    InvoiceResponse toInvoiceResponse(Invoices invoice);
    void updateInvoice(@MappingTarget Invoices invoice, InvoiceUpdateRequest request);
}

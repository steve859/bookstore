package com.bookstore.mapper;

import com.bookstore.dto.request.InvoiceCreationRequest;
import com.bookstore.dto.request.InvoiceUpdateRequest;
import com.bookstore.dto.response.BookUpdateResponse;
import com.bookstore.dto.response.InvoiceResponse;
import com.bookstore.entity.BooksImportReceipts;
import com.bookstore.entity.BooksInvoices;
import com.bookstore.entity.Invoices;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface InvoiceMapper {
    Invoices toInvoice(InvoiceCreationRequest request);
    @Mapping(target = "bookDetails", expression = "java(mapBookDetails(invoice.getBookDetails()))")
    InvoiceResponse toInvoiceResponse(Invoices invoice);
    default Set<BookUpdateResponse> mapBookDetails(Set<BooksInvoices> bookInvoices) {
        if (bookInvoices == null) return null;
        return bookInvoices.stream().map(bir -> {
            var book = bir.getBook();
            return BookUpdateResponse.builder()
                    .bookId(book.getBookId())
                    .name(book.getName())
                    .importPrice(book.getImportPrice())
                    .quantity(bir.getQuantity())
                    .build();
        }).collect(Collectors.toSet());
    }
    void updateInvoice(@MappingTarget Invoices invoice, InvoiceUpdateRequest request);
}

package com.bookstore.mapper;

import com.bookstore.dto.request.ImportReceiptCreationRequest;
import com.bookstore.dto.request.ImportReceiptUpdateRequest;
import com.bookstore.dto.response.BookUpdateResponse;
import com.bookstore.dto.response.ImportReceiptResponse;
import com.bookstore.entity.BooksImportReceipts;
import com.bookstore.entity.ImportReceipts;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ImportReceiptMapper {
    ImportReceipts toImportReceipt(ImportReceiptCreationRequest request);


    @Mapping(target = "bookDetails", expression = "java(mapBookDetails(importReceipt.getBookDetails()))")
    ImportReceiptResponse toImportReceiptResponse(ImportReceipts importReceipt);

    // Custom mapper cho danh sách sách trong phiếu nhập
    default Set<BookUpdateResponse> mapBookDetails(Set<BooksImportReceipts> bookImportReceipts) {
        if (bookImportReceipts == null) return null;
        return bookImportReceipts.stream().map(bir -> {
            var book = bir.getBook();
            return BookUpdateResponse.builder()
                    .bookId(book.getBookId())
                    .name(book.getName())
                    .importPrice(bir.getImportPrice())
                    .quantity(bir.getQuantity())
                    .build();
        }).collect(Collectors.toSet());
    }


    void updateImportReceipts(@MappingTarget ImportReceipts importReceipt, ImportReceiptUpdateRequest request);
}

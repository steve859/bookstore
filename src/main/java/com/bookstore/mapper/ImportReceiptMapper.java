package com.bookstore.mapper;

import com.bookstore.dto.request.ImportReceiptCreationRequest;
import com.bookstore.dto.request.ImportReceiptUpdateRequest;
import com.bookstore.dto.response.ImportReceiptResponse;
import com.bookstore.entity.ImportReceipts;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ImportReceiptMapper {
    ImportReceipts toImportReceipt(ImportReceiptCreationRequest request);

    ImportReceiptResponse toImportReceiptResponse(ImportReceipts importReceipt);

    void updateImportReceipts(@MappingTarget ImportReceipts importReceipt, ImportReceiptUpdateRequest request);
}

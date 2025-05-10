package com.bookstore.mapper;

import com.bookstore.dto.request.ImportReceiptCreationRequest;
import com.bookstore.dto.request.ImportReceiptUpdateRequest;
import com.bookstore.dto.response.ImportReceiptRespones;
import com.bookstore.entity.ImportReceipts;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ImportReceiptMapper {
    ImportReceipts toImportReceipt(ImportReceiptCreationRequest request);

    ImportReceiptRespones toImportReceiptRespones(ImportReceipts importReceipt);

    void updateImportReceipts(@MappingTarget ImportReceipts importReceipt, ImportReceiptUpdateRequest request);
}

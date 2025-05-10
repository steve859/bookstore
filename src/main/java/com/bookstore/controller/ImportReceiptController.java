package com.bookstore.controller;

import com.bookstore.dto.request.ApiResponse;
import com.bookstore.dto.request.ImportReceiptCreationRequest;
import com.bookstore.dto.request.ImportReceiptUpdateRequest;
import com.bookstore.dto.response.ImportReceiptRespones;
import com.bookstore.entity.ImportReceipts;
import com.bookstore.repository.CategoryRepository;
import com.bookstore.service.CategoryService;
import com.bookstore.service.ImportReceiptService;
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
@RequestMapping("/importReceipts")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ImportReceiptController {
    @Autowired
    private ImportReceiptService importReceiptService;

    @PostMapping
    ApiResponse<ImportReceiptRespones> createImportReceipt(@RequestBody ImportReceiptCreationRequest request) {
        return ApiResponse.<ImportReceiptRespones>builder().result(importReceiptService.createImportReceipt(request)).build();
    }

    @GetMapping
    ApiResponse<List<ImportReceiptRespones>> getImportReceipts() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        authentication.getAuthorities().forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));
        return ApiResponse.<List<ImportReceiptRespones>>builder().result(importReceiptService.getImportReceipts()).build();
    }

    @GetMapping("{/importReceiptId}")
    ApiResponse<ImportReceiptRespones> getImportReceipt(@PathVariable("importReceiptId") Integer importReceiptId) {
        return ApiResponse.<ImportReceiptRespones>builder().result(importReceiptService.getImportReceipt(importReceiptId)).build();
    }

    @PutMapping("{/importReceiptId}")
    ApiResponse<ImportReceiptRespones> updateImportReceipt(@PathVariable("importReceiptId") Integer importReceiptId, @RequestBody ImportReceiptUpdateRequest request) {
        return ApiResponse.<ImportReceiptRespones>builder().result(importReceiptService.updateImportReceipt(importReceiptId, request)).build();
    }

    @DeleteMapping("{/importReceiptId}")
    ApiResponse<String> deleteImportReceipt(@PathVariable("importReceiptId") Integer importReceiptId) {
        importReceiptService.deleteImportReceipt(importReceiptId);
        return ApiResponse.<String>builder().result("Deleted has been deleted").build();
    }
}

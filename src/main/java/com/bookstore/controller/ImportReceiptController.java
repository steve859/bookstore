package com.bookstore.controller;

import java.util.List;

import com.bookstore.dto.response.ImportReceiptResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.dto.request.ApiResponse;
import com.bookstore.dto.request.ImportReceiptCreationRequest;
import com.bookstore.dto.request.ImportReceiptUpdateRequest;
import com.bookstore.service.ImportReceiptService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/import")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ImportReceiptController {
    @Autowired
    private ImportReceiptService importReceiptService;

    @PostMapping
    ApiResponse<ImportReceiptResponse> createImportReceipt(@RequestBody ImportReceiptCreationRequest request) {
        return ApiResponse.<ImportReceiptResponse>builder().result(importReceiptService.createImportReceipt(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<ImportReceiptResponse>> getImportReceipts() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        authentication.getAuthorities().forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));
        return ApiResponse.<List<ImportReceiptResponse>>builder().result(importReceiptService.getImportReceipts())
                .build();
    }

    @GetMapping("/{importReceiptId}")
    ApiResponse<ImportReceiptResponse> getImportReceipt(@PathVariable("importReceiptId") Integer importReceiptId) {
        return ApiResponse.<ImportReceiptResponse>builder()
                .result(importReceiptService.getImportReceipt(importReceiptId)).build();
    }

    @PutMapping("/{importReceiptId}")
    ApiResponse<ImportReceiptResponse> updateImportReceipt(@PathVariable("importReceiptId") Integer importReceiptId,
            @RequestBody ImportReceiptUpdateRequest request) {
        return ApiResponse.<ImportReceiptResponse>builder()
                .result(importReceiptService.updateImportReceipt(importReceiptId, request)).build();
    }

    @DeleteMapping("/{importReceiptId}")
    ApiResponse<String> deleteImportReceipt(@PathVariable("importReceiptId") Integer importReceiptId) {
        importReceiptService.deleteImportReceipt(importReceiptId);
        return ApiResponse.<String>builder().result("Deleted has been deleted").build();
    }
}

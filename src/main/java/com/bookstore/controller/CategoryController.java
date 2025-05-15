package com.bookstore.controller;

import java.util.List;

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
import com.bookstore.dto.request.CategoryCreationRequest;
import com.bookstore.dto.request.CategoryUpdateRequest;
import com.bookstore.dto.response.CategoryResponse;
import com.bookstore.service.CategoryService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/categories")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping
    ApiResponse<CategoryResponse> createCategory(@RequestBody CategoryCreationRequest request) {
        log.debug("Received request to create category: {}", request);
        return ApiResponse.<CategoryResponse>builder()
                .result(categoryService.createCategory(request))
                .build();
    }

    @GetMapping
    ApiResponse<List<CategoryResponse>> getCategories() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        authentication.getAuthorities().forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));
        return ApiResponse.<List<CategoryResponse>>builder().result(categoryService.getCategories()).build();
    }

    @GetMapping("/{categoryId}")
    ApiResponse<CategoryResponse> getCategory(@PathVariable("categoryId") Integer categoryId) {
        return ApiResponse.<CategoryResponse>builder().result(categoryService.getCategory(categoryId)).build();
    }

    @PutMapping("/{categoryId}")
    ApiResponse<CategoryResponse> updateCategory(@PathVariable("categoryId") Integer categoryId,
            @RequestBody CategoryUpdateRequest request) {
        return ApiResponse.<CategoryResponse>builder().result(categoryService.updateCategory(categoryId, request))
                .build();
    }

    @DeleteMapping("/{categoryId}")
    ApiResponse<String> deleteCategory(@PathVariable("categoryId") Integer categoryId) {
        categoryService.deleteCategory(categoryId);
        return ApiResponse.<String>builder().result("Category has been deleted").build();
    }
}

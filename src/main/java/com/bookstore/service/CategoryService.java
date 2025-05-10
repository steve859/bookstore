package com.bookstore.service;

import com.bookstore.dto.request.ApiResponse;
import com.bookstore.dto.request.CategoryCreationRequest;
import com.bookstore.dto.request.CategoryUpdateRequest;
import com.bookstore.dto.response.CategoryResponse;
import com.bookstore.entity.Categories;
import com.bookstore.exception.AppException;
import com.bookstore.exception.ErrorCode;
import com.bookstore.mapper.CategoryMapper;
import com.bookstore.repository.CategoryRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;
    CategoryMapper categoryMapper;

    public CategoryResponse createCategory(CategoryCreationRequest request) {
        Categories category = categoryMapper.toCategory(request);
        return categoryMapper.toCategoryResponse(categoryRepository.save(category));
    }

    public List<CategoryResponse> getCategories() {
        return categoryRepository.findAll().stream().map(categoryMapper::toCategoryResponse).toList();
    }

    public CategoryResponse getCategory(Integer categoryId) {
        return categoryMapper.toCategoryResponse(categoryRepository.findById(categoryId).orElseThrow(()->new RuntimeException("Category not found")));
    }

    public CategoryResponse updateCategory(Integer categoryId,CategoryUpdateRequest request) {
        Categories category = categoryRepository.findById(categoryId).orElseThrow(()->new AppException(ErrorCode.CATEGORY_NOT_EXISTED));
        categoryMapper.updateCategory(category, request);
        return categoryMapper.toCategoryResponse(categoryRepository.save(category));
    }

    public void deleteCategory(@PathVariable("categoryId") Integer categoryId) {
        Categories category = categoryRepository.findById(categoryId).orElse(null);
        if (category != null) {
            categoryRepository.delete(category);
        }
    }
}

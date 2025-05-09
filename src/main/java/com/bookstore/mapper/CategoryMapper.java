package com.bookstore.mapper;

import com.bookstore.dto.request.CategoryCreationRequest;
import com.bookstore.dto.request.CategoryUpdateRequest;
import com.bookstore.dto.response.CategoryResponse;
import com.bookstore.entity.Categories;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    Categories toCategory(CategoryCreationRequest request);
    CategoryResponse toCategoryResponse(Categories category);
    void updateCategory(Categories category, CategoryUpdateRequest request);
}

package com.bookstore.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.bookstore.dto.request.BookCreationRequest;
import com.bookstore.dto.request.BookUpdateRequest;
import com.bookstore.dto.response.BookResponse;
import com.bookstore.entity.Books;

@Mapper(componentModel = "spring")
public interface BookMapper {
    @Mapping(target = "authors", ignore = true)
    @Mapping(target = "categories", ignore = true)
    Books toBook(BookCreationRequest request);

    // @Mapping(target = "authors", ignore = true)
    // @Mapping(target = "categories", ignore = true)
    // Books toBook(BookUpdateRequest request);

    BookResponse toBookResponse(Books book);

    @Mapping(target = "authors", ignore = true)
    @Mapping(target = "categories", ignore = true)
    void updateBook(@MappingTarget Books book, BookUpdateRequest request);
}

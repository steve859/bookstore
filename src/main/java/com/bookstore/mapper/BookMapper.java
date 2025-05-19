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
    @Mapping(target = "bookId", ignore = true)
    Books toBook(BookCreationRequest request);

    BookResponse toBookResponse(Books book);

    void updateBook(@MappingTarget Books book, BookUpdateRequest request);
}

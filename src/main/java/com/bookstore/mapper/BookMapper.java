package com.bookstore.mapper;

import com.bookstore.dto.request.BookCreationRequest;
import com.bookstore.dto.request.BookUpdateRequest;
import com.bookstore.dto.response.BookResponse;
import com.bookstore.entity.Books;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface BookMapper {
    Books toBook(BookCreationRequest request);

    BookResponse toBookResponse(Books book);

    void updateBook(@MappingTarget Books book, BookUpdateRequest request);
}

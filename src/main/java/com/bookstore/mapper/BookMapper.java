package com.bookstore.mapper;

import com.bookstore.dto.request.BookCreationRequest;
import com.bookstore.dto.response.BookResponse;
import com.bookstore.entity.Book;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BookMapper {
    Book toBook(BookCreationRequest request);
    BookResponse toBookResponse(Book book);
    void updateBook(Book book, BookCreationRequest request);
}

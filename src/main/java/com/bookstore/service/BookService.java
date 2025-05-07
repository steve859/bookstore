package com.bookstore.service;

import com.bookstore.dto.request.BookCreationRequest;
import com.bookstore.dto.response.BookResponse;
import com.bookstore.entity.Books;
import com.bookstore.exception.AppException;
import com.bookstore.exception.ErrorCode;
import com.bookstore.mapper.BookMapper;
import com.bookstore.repository.BookRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class BookService {
    @Autowired
    BookRepository bookRepository;
    BookMapper bookMapper;

    public BookResponse createBook(BookCreationRequest request) {
        if (request.getQuantity() >= 30) {
            throw new AppException(ErrorCode.BOOK_QUANTITY_EXCEEDED);
        }
        Books book = bookMapper.toBook(request);
        return bookMapper.toBookResponse(bookRepository.save(book));
    }

    public List<BookResponse> getBooks() {
        return bookRepository.findAll().stream().map(bookMapper::toBookResponse).toList();
    }

    public BookResponse getBook(Integer bookId) {
        return bookMapper.toBookResponse(
                bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found")));
    }

    public void deleteBook(Integer bookId) {
        Books book = bookRepository.findById(bookId).orElse(null);
        if (book != null) {
            bookRepository.delete(book);
        }
    }

    public BookResponse updateBook(Integer bookId, BookCreationRequest request) {
        Books book = bookRepository.findById(bookId).orElseThrow(() -> new AppException(ErrorCode.BOOK_NOT_EXISTED));
        bookMapper.updateBook(book, request);
        return bookMapper.toBookResponse(bookRepository.save(book));
    }
}

package com.bookstore.controller;

import com.bookstore.dto.request.ApiResponse;
import com.bookstore.dto.request.BookCreationRequest;
import com.bookstore.dto.response.BookResponse;
import com.bookstore.dto.response.UserResponse;
import com.bookstore.entity.Book;
import com.bookstore.repository.BookRepository;
import com.bookstore.service.BookService;
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
@RequestMapping("/books")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class BookController {
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BookService bookService;

    @PostMapping
    ApiResponse<BookResponse> createBook(@RequestBody BookCreationRequest request) {
        return ApiResponse.<BookResponse>builder().result(bookService.createBook(request)).build();
    }

    @GetMapping
    ApiResponse<List<BookResponse>> getBooks() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        authentication.getAuthorities().forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));
        return ApiResponse.<List<BookResponse>>builder().result(bookService.getBooks()).build();
    }

    @GetMapping("/{bookId}")
    ApiResponse<BookResponse> getBook(@PathVariable("bookId") String bookId) {
        return ApiResponse.<BookResponse>builder().result(bookService.getBook(bookId)).build();
    }

    @PutMapping("/{bookId}")
    ApiResponse<BookResponse> updateBook(@PathVariable("bookId") String bookId,@RequestBody BookCreationRequest request) {
        return ApiResponse.<BookResponse>builder().result(bookService.updateBook(bookId,request)).build();
    }

    @DeleteMapping("/{bookId}")
    ApiResponse<String> deleteBook(@PathVariable("bookId") String bookId) {
        bookService.deleteBook(bookId);
        return ApiResponse.<String>builder().result("Book has been deleted").build();
    }
}

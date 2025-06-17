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
import com.bookstore.dto.request.BookCreationRequest;
import com.bookstore.dto.request.BookUpdateRequest;
import com.bookstore.dto.response.BookResponse;
import com.bookstore.entity.Books;
import com.bookstore.service.BookService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class BookController {
    @Autowired
    private BookService bookService;

    @PostMapping
    ApiResponse<Books> createBook(@RequestBody BookCreationRequest request) {
        return ApiResponse.<Books>builder().result(bookService.createBook(request)).build();
    }

    @GetMapping
    ApiResponse<List<BookResponse>> getBooks() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        authentication.getAuthorities().forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));
        return ApiResponse.<List<BookResponse>>builder().result(bookService.getBooks()).build();
    }

    @GetMapping("/{bookId}")
    ApiResponse<Books> getBook(@PathVariable("bookId") Integer bookId) {
        return ApiResponse.<Books>builder().result(bookService.getBook(bookId)).build();
    }

    @GetMapping("/dto/{bookId}")
    ApiResponse<BookResponse> getBookDTOById(@PathVariable Integer bookId) {
        return ApiResponse.<BookResponse>builder()
                .result(bookService.getBookDTOById(bookId))
                .build();
    }

    @PutMapping("/{bookId}")
    ApiResponse<BookResponse> updateBook(@PathVariable("bookId") Integer bookId,
            @RequestBody BookUpdateRequest request) {
        return ApiResponse.<BookResponse>builder().result(bookService.updateBook(bookId, request)).build();
    }

    @DeleteMapping("/{bookId}")
    ApiResponse<String> deleteBook(@PathVariable("bookId") Integer bookId) {
        bookService.deleteBook(bookId);
        return ApiResponse.<String>builder().result("Book has been deleted").build();
    }

    @PutMapping("/edit/{bookId}")
    ApiResponse<BookResponse> editBook(@PathVariable Integer bookId, @RequestBody BookCreationRequest entity) {
        return ApiResponse.<BookResponse>builder().result(bookService.editBook(bookId, entity)).build();
    }
}

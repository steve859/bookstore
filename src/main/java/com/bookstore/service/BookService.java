package com.bookstore.service;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.bookstore.dto.request.BookCreationRequest;
import com.bookstore.dto.request.BookUpdateRequest;
import com.bookstore.dto.response.BookResponse;
import com.bookstore.entity.Authors;
import com.bookstore.entity.Books;
import com.bookstore.entity.Categories;
import com.bookstore.exception.AppException;
import com.bookstore.exception.ErrorCode;
import com.bookstore.mapper.BookMapper;
import com.bookstore.repository.AuthorRepository;
import com.bookstore.repository.BookRepository;
import com.bookstore.repository.CategoryRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import static java.util.Collections.sort;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class BookService {
    @Autowired
    AuthorRepository authorRepository;
    CategoryRepository categoryRepository;
    BookRepository bookRepository;
    BookMapper bookMapper;

    private Set<Authors> resolveAuthors(List<String> authorsString) {
        Set<Authors> authorsSet = new HashSet<>();
        for (String authorString : authorsString) {
            Authors author = authorRepository.findByAuthorName(authorString)
                    .orElseGet(() -> {
                        Authors newAuthor = Authors.builder()
                                .authorName(authorString)
                                .build();
                        return authorRepository.save(newAuthor);
                    });
            authorsSet.add(author);
        }
        return authorsSet;
    }

    private Set<Categories> resolveCategories(List<String> categoriesString) {
        Set<Categories> categoriesSet = new HashSet<>();
        for (String categoryString : categoriesString) {
            Categories category = categoryRepository.findByCategoryName(categoryString)
                    .orElseGet(() -> {
                        Categories newCategory = Categories.builder()
                                .categoryName(categoryString)
                                .build();
                        return categoryRepository.save(newCategory);
                    });
            categoriesSet.add(category);
        }
        return categoriesSet;
    }

    private Optional<Books> IsBookAvailable(String Name, List<String> inputAuthorNames) {
        List<Books> booksWithSameName = bookRepository.findByName(Name);
        if (booksWithSameName.isEmpty()) {
            return Optional.empty();
        }
        Collections.sort(inputAuthorNames);
        for(Books book : booksWithSameName) {
            List<String> existedAuthorNames = book.getAuthors().stream()
                    .map(Authors::getAuthorName)
                    .sorted()
                    .collect(Collectors.toList());
            if(inputAuthorNames.equals(existedAuthorNames)) {
                return Optional.of(book);
            }
        }
        return Optional.empty();
    };

    public BookResponse createBook(BookCreationRequest request) {
        Optional<Books> bookAvailable = IsBookAvailable(request.getName(), request.getAuthors());
        Books book;
        if(bookAvailable.isPresent()) {
            book = bookAvailable.get();
            if(book.getQuantity()>=300){
                throw new AppException(ErrorCode.BOOK_QUANTITY_EXCEEDED);
            }
            book.setQuantity(book.getQuantity() + request.getQuantity());
        }
        else {
            book = bookMapper.toBook(request);
            // Xử lý authors
            book.setAuthors(resolveAuthors(request.getAuthors()));
            // Xử lý categories
            book.setCategories(resolveCategories(request.getCategories()));
        }
        Books savedBook = bookRepository.save(book);
        return bookMapper.toBookResponse(savedBook);
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

    public BookResponse updateBook(Integer bookId, BookUpdateRequest request) {
        Books book = bookRepository.findById(bookId).orElseThrow(() -> new AppException(ErrorCode.BOOK_NOT_EXISTED));
        bookMapper.updateBook(book, request);
        return bookMapper.toBookResponse(bookRepository.save(book));
    }
}

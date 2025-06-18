package com.bookstore.service;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class BookService {
    @Autowired
    AuthorRepository authorRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    BookMapper bookMapper;
    @Autowired
    private MonthlyInventoryReportDetailService monthlyInventoryReportDetailService;

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

    private Optional<Books> IsBookAvailable(String Name, List<String> inputAuthorNames, Integer publishedYear) {
        List<Books> booksWithSameName = bookRepository.findByName(Name);
        if (booksWithSameName.isEmpty()) {
            return Optional.empty();
        }
        for (Books book : booksWithSameName) {
            if (inputAuthorNames.size() != book.getAuthors().size()) {
                continue;
            }
            if (publishedYear != book.getPublishedYear()) {
                continue;
            }
            return Optional.of(book);
        }
        return Optional.empty();
    }

    @Transactional
    public Books createBook(BookCreationRequest request) {
        Books book;
        Optional<Books> bookAvailable = IsBookAvailable(request.getName(), request.getAuthors(),
                request.getPublishedYear());
        if (bookAvailable.isEmpty()) {
            request.setImportPrice(new BigDecimal(0));
            request.setQuantity(0);
            book = bookMapper.toBook(request);
            book.setAuthors(resolveAuthors(request.getAuthors()));
            book.setCategories(resolveCategories(request.getCategories()));
        } else {
            throw new AppException(ErrorCode.BOOK_EXISTED);
        }
        return bookRepository.save(book);
    }

    public List<BookResponse> getBooks() {
        return bookRepository.findAll().stream().map(bookMapper::toBookResponse).toList();
    }

    public Books getBook(Integer bookId) {
        return bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found"));
    }

    public BookResponse getBookDTOById(Integer bookId) {
        return bookMapper.toBookResponse(
                bookRepository.findById(bookId).orElseThrow(() -> new RuntimeException("Book not found")));
    }

    public void deleteBook(Integer bookId) {
        Books book = bookRepository.findById(bookId).orElse(null);
        if (book != null) {
            bookRepository.delete(book);
        }
    }

    // public BookResponse updateBook(Integer bookId, BookUpdateRequest request) {
    // Books book = bookRepository.findById(bookId)
    // .orElseThrow(() -> new AppException(ErrorCode.BOOK_NOT_EXISTED));
    // bookMapper.updateBook(book, request);
    // return bookMapper.toBookResponse(bookRepository.save(book));
    // }

    public BookResponse updateBook(Integer bookId, BookUpdateRequest request) {
        Books book = bookRepository.findById(bookId).orElse(null);
        if (book == null) {
            throw new AppException(ErrorCode.BOOK_NOT_EXISTED);
        }
        monthlyInventoryReportDetailService.createMonthlyInventoryReportDetail(bookId, request.getQuantity(), "Import");
        int temp = book.getQuantity() + request.getQuantity();
        book.setImportPrice(request.getImportPrice());
        book.setQuantity(temp);
        return bookMapper.toBookResponse(bookRepository.save(book));
    }

    public BookResponse editBook(Integer bookId, BookCreationRequest request) {
        Books book = bookRepository.findById(bookId)
                .orElseThrow(() -> new AppException(ErrorCode.BOOK_NOT_EXISTED));
        book.setName(request.getName());
        book.setPublishedYear(request.getPublishedYear());
        book.setImportPrice(request.getImportPrice());
        // book.setQuantity(request.getQuantity());
        book.setAuthors(resolveAuthors(request.getAuthors()));
        book.setCategories(resolveCategories(request.getCategories()));
        Books updated = bookRepository.save(book);
        return bookMapper.toBookResponse(bookRepository.save(updated));
    }

    public BookResponse updateBookQuantity(Integer bookId, Integer quantity) {
        Books book = bookRepository.findById(bookId).orElse(null);
        book.setQuantity(book.getQuantity() + quantity);
        return bookMapper.toBookResponse(bookRepository.save(book));
    }
}

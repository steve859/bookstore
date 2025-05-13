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
import com.bookstore.dto.request.AuthorCreationRequest;
import com.bookstore.dto.request.AuthorUpdateRequest;
import com.bookstore.dto.response.AuthorResponse;
import com.bookstore.service.AuthorService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/authors")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AuthorController {
    @Autowired
    private AuthorService authorService;

    @PostMapping
    ApiResponse<AuthorResponse> createAuthor(@RequestBody AuthorCreationRequest request) {
        return ApiResponse.<AuthorResponse>builder().result(authorService.createAuthor(request)).build();
    }

    @GetMapping
    ApiResponse<List<AuthorResponse>> getAuthors() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        authentication.getAuthorities().forEach(grantedAuthority -> {
            log.info(grantedAuthority.getAuthority());
        });
        return ApiResponse.<List<AuthorResponse>>builder().result(authorService.getAuthors()).build();
    }

    @GetMapping("/{authorId}")
    ApiResponse<AuthorResponse> getAuthor(@PathVariable("authorId") Integer authorId) {
        return ApiResponse.<AuthorResponse>builder().result(authorService.getAuthor(authorId)).build();
    }

    @PutMapping("/{authorId}")
    ApiResponse<AuthorResponse> updateAuthor(@PathVariable("authorId") Integer authorId,
            @RequestBody AuthorUpdateRequest request) {
        return ApiResponse.<AuthorResponse>builder().result(authorService.updateAuthor(authorId, request)).build();
    }

    @DeleteMapping("/{authorId}")
    ApiResponse<String> deleteAuthor(@PathVariable("authorId") Integer authorId) {
        authorService.deleteAuthor(authorId);
        return ApiResponse.<String>builder().result("Author has been deleted").build();
    }
}

package com.bookstore.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookstore.dto.request.AuthorCreationRequest;
import com.bookstore.dto.request.AuthorUpdateRequest;
import com.bookstore.dto.response.AuthorResponse;
import com.bookstore.entity.Authors;
import com.bookstore.exception.AppException;
import com.bookstore.exception.ErrorCode;
import com.bookstore.mapper.AuthorMapper;
import com.bookstore.mapper.BookMapper;
import com.bookstore.repository.AuthorRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class AuthorService {
    @Autowired
    AuthorRepository authorRepository;
    AuthorMapper authorMapper;
    @Autowired
    private BookMapper bookMapper;

    public AuthorResponse createAuthor(AuthorCreationRequest request) {
        Authors author = authorMapper.toAuthor(request);
        log.info(request.getAuthorName());
        return authorMapper.toAuthorResponse(authorRepository.save(author));
    }

    public List<AuthorResponse> getAuthors() {
        return authorRepository.findAll().stream().map(authorMapper::toAuthorResponse).toList();
    }

    public AuthorResponse getAuthor(Integer authorId) {
        return authorMapper.toAuthorResponse(
                authorRepository.findById(authorId).orElseThrow(() -> new RuntimeException("Author not found")));
    }

    public AuthorResponse updateAuthor(Integer authorId, AuthorUpdateRequest request) {
        Authors author = authorRepository.findById(authorId)
                .orElseThrow(() -> new AppException(ErrorCode.AUTHOR_NOT_EXISTED));
        authorMapper.updateAuthor(author, request);
        return authorMapper.toAuthorResponse(authorRepository.save(author));
    }

    public void deleteAuthor(Integer authorId) {
        Authors author = authorRepository.findById(authorId).orElse(null);
        if (author != null) {
            authorRepository.delete(author);
        }
    }
}

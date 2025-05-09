package com.bookstore.mapper;

import com.bookstore.dto.request.AuthorCreationRequest;
import com.bookstore.dto.request.AuthorUpdateRequest;
import com.bookstore.dto.response.AuthorResponse;
import com.bookstore.entity.Authors;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    Authors toAuthor(AuthorCreationRequest request);
    AuthorResponse toAuthorResponse(Authors author);
    void updateAuthor(@MappingTarget Authors author, AuthorUpdateRequest request);
}

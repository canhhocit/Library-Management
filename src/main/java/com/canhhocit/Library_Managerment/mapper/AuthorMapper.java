package com.canhhocit.Library_Managerment.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.canhhocit.Library_Managerment.dto.request.AuthorRequest;
import com.canhhocit.Library_Managerment.dto.response.AuthorResponse;
import com.canhhocit.Library_Managerment.entities.Author;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "books", ignore = true)
    Author toAuthor(AuthorRequest authorRequest);

    AuthorResponse toAuthorResponse(Author author);

    
}

package com.canhhocit.Library_Managerment.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.canhhocit.Library_Managerment.dto.response.BookResponse;
import com.canhhocit.Library_Managerment.entities.Book;

@Mapper(componentModel = "spring")
public interface BookMapper {
    
    @Mapping(target = "category", source = "category")
    @Mapping(target = "authors", source = "authors")
    BookResponse toBookResponse(Book book);
}
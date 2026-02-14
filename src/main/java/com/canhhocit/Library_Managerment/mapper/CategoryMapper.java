package com.canhhocit.Library_Managerment.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.canhhocit.Library_Managerment.dto.request.CategoryRequest;
import com.canhhocit.Library_Managerment.dto.response.CategoryResponse;
import com.canhhocit.Library_Managerment.entities.Category;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "books", ignore = true)  
    Category toCategory(CategoryRequest request);

    CategoryResponse toCategoryResponse(Category category);
}

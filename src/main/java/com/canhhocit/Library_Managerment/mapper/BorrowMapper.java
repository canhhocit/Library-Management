package com.canhhocit.Library_Managerment.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.canhhocit.Library_Managerment.dto.response.BorrowDetailResponse;
import com.canhhocit.Library_Managerment.dto.response.BorrowResponse;
import com.canhhocit.Library_Managerment.entities.Borrow;
import com.canhhocit.Library_Managerment.entities.BorrowDetail;

@Mapper(componentModel = "spring")
public interface BorrowMapper {
    
    @Mapping(target = "user", source = "user")
    @Mapping(target = "borrowDetails", source = "borrowDetails")
    BorrowResponse toBorrowResponse(Borrow borrow);
    
    @Mapping(target = "bookId", source = "book.id")
    @Mapping(target = "bookTitle", source = "book.title")
    BorrowDetailResponse toBorrowDetailResponse(BorrowDetail borrowDetail);
}
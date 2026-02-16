package com.canhhocit.Library_Managerment.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookResponse {
    Long id;
    String title;
    String description;
    Integer publishYear;
    Integer quantity;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    CategoryResponse category;
    List<AuthorResponse> authors;
}
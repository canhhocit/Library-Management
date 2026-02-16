package com.canhhocit.Library_Managerment.dto.request;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookRequest {
    String title;
    String description;
    Integer publishYear;
    Integer quantity;
    Long categoryId;
    List<Long> authorIds;
}
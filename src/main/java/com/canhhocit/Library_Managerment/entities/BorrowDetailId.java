package com.canhhocit.Library_Managerment.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.Embeddable;
import java.io.Serializable;


@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BorrowDetailId implements Serializable {
    
    private Long borrowId;
    private Long bookId;
}
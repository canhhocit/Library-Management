package com.canhhocit.Library_Managerment.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Entity
@Table(name = "borrow_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BorrowDetail {
    
    @EmbeddedId
    private BorrowDetailId id;
    
    @ManyToOne
    @MapsId("borrowId")
    @JoinColumn(name = "borrow_id")
    private Borrow borrow;
    
    @ManyToOne
    @MapsId("bookId")
    @JoinColumn(name = "book_id")
    private Book book;
    
    @Column(nullable = false)
    private Integer quantity = 1;
}
package com.canhhocit.Library_Managerment.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "borrows")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Borrow {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "borrow_date")
    private LocalDateTime borrowDate = LocalDateTime.now();
    
    @Column(name = "return_date")
    private LocalDateTime returnDate;
    
    @Column(length = 20)
    private String status = "BORROWING";  // BORROWING, RETURNED
    
    //N-1  User
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    //1-N  BorrowDetail
    @OneToMany(mappedBy = "borrow", cascade = CascadeType.ALL)
    private List<BorrowDetail> borrowDetails;
}
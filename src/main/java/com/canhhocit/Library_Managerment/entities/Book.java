package com.canhhocit.Library_Managerment.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, length = 255)
    private String title;
    
    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "publish_year")
    private Integer publishYear;
    
    @Column(nullable = false)
    private Integer quantity = 0;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt = LocalDateTime.now();
    
    @Column(name = "is_deleted")
    private Boolean isDeleted = false;
    
    // Quan hệ N-1 với Category
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    
    // Quan hệ N-N với Author
    @ManyToMany
    @JoinTable(
        name = "book_authors",
        joinColumns = @JoinColumn(name = "book_id"),
        inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private List<Author> authors;
    
    // Quan hệ 1-N với BorrowDetail
    @OneToMany(mappedBy = "book")
    private List<BorrowDetail> borrowDetails;
}
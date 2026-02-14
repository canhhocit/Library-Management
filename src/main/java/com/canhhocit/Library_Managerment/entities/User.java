package com.canhhocit.Library_Managerment.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
@Data  
@NoArgsConstructor  
@AllArgsConstructor 
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true, length = 50)
    private String username;
    @Column(nullable = false, length = 255)
    private String password;
    @Column(name = "full_name", length = 100)
    private String fullName;
    @Column(length = 20)
    private String role = "USER";  // USER, ADMIN, LIBRARIAN
    @Column(length = 20)
    private String status = "ACTIVE";  // ACTIVE, INACTIVE
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
     
    // Quan hệ 1-N với Borrow
    @OneToMany(mappedBy = "user")
    private List<Borrow> borrows;
}
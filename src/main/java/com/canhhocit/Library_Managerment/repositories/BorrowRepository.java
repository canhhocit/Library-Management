package com.canhhocit.Library_Managerment.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.canhhocit.Library_Managerment.entities.Borrow;

import java.util.List;

@Repository
public interface BorrowRepository extends JpaRepository<Borrow, Long> {
    
    // Tìm lượt mượn theo user
    List<Borrow> findByUserId(Long userId);
    
    // Tìm lượt mượn theo status
    List<Borrow> findByStatus(String status);
}
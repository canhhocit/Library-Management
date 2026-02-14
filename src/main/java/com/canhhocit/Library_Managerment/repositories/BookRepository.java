package com.canhhocit.Library_Managerment.repositories;


import com.canhhocit.Library_Managerment.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    
    List<Book> findByTitle(String title);
    
    List<Book> findByCategoryId(Long categoryId);
    
    // Tìm sách chưa bị xóa (Soft Delete)
    List<Book> findByIsDeletedFalse();
    
    // Tìm sách theo title và chưa bị xóa
    List<Book> findByTitleContainingAndIsDeletedFalse(String keyword);
    
    List<Book> findByPublishYear(Integer year);
    
    // availid books
    @Query("SELECT b FROM Book b WHERE b.quantity > 0 AND b.isDeleted = false")
    List<Book> findAvailableBooks();
    // Invailid books
    @Query("SELECT b FROM Book b WHERE b.isDeleted = true")
    List<Book> findInvailableBooks();
}
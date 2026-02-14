package com.canhhocit.Library_Managerment.services;


import com.canhhocit.Library_Managerment.entities.*;
import com.canhhocit.Library_Managerment.repositories.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Service xử lý business logic cho Book
 */
@Service
@RequiredArgsConstructor  // Tự động inject dependencies qua constructor
public class BookService {
    
    private final BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findByIsDeletedFalse();
    }
    
    public Book getBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sách với ID: " + id));
    }
    
    public Book createBook(Book book) {
        book.setCreatedAt(LocalDateTime.now());
        book.setUpdatedAt(LocalDateTime.now());
        book.setIsDeleted(false);
        return bookRepository.save(book);
    }
    
    public Book updateBook(Long id, Book bookDetails) {
        Book book = getBookById(id);
        
        book.setTitle(bookDetails.getTitle());
        book.setDescription(bookDetails.getDescription());
        book.setPublishYear(bookDetails.getPublishYear());
        book.setQuantity(bookDetails.getQuantity());
        book.setCategory(bookDetails.getCategory());
        book.setAuthors(bookDetails.getAuthors());
        book.setUpdatedAt(LocalDateTime.now());
        
        return bookRepository.save(book);
    }

    public void deleteBook(Long id) {
        Book book = getBookById(id);
        book.setIsDeleted(true);
        book.setUpdatedAt(LocalDateTime.now());
        bookRepository.save(book);
    }
    
    public List<Book> searchBooks(String keyword) {
        return bookRepository.findByTitleContainingAndIsDeletedFalse(keyword);
    }
    
    /**
     * Lấy sách có sẵn (còn trong kho)
     */
    public List<Book> getAvailableBooks() {
        return bookRepository.findAvailableBooks();
    }
}
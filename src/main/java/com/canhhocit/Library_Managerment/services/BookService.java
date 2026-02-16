package com.canhhocit.Library_Managerment.services;

import com.canhhocit.Library_Managerment.dto.request.BookRequest;
import com.canhhocit.Library_Managerment.dto.response.ApiResponse;
import com.canhhocit.Library_Managerment.dto.response.BookResponse;
import com.canhhocit.Library_Managerment.entities.*;
import com.canhhocit.Library_Managerment.exception.AppException;
import com.canhhocit.Library_Managerment.exception.ErrorCode;
import com.canhhocit.Library_Managerment.mapper.BookMapper;
import com.canhhocit.Library_Managerment.repositories.*;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookService {
    
    BookRepository bookRepository;
    CategoryRepository categoryRepository;
    AuthorRepository authorRepository;
    BookMapper bookMapper;

    public ApiResponse<List<BookResponse>> getAllBooks() {
        List<BookResponse> books = bookRepository.findByIsDeletedFalse()
                .stream()
                .map(bookMapper::toBookResponse)
                .collect(Collectors.toList());
        
        return ApiResponse.<List<BookResponse>>builder()
                .code(1000)
                .message("Lấy danh sách sách thành công")
                .result(books)
                .build();
    }
    
    public ApiResponse<BookResponse> getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.BOOK_NOT_FOUND));
        
        if (book.getIsDeleted()) {
            throw new AppException(ErrorCode.BOOK_NOT_FOUND);
        }
        
        return ApiResponse.<BookResponse>builder()
                .code(1000)
                .message("Lấy thông tin sách thành công")
                .result(bookMapper.toBookResponse(book))
                .build();
    }
    
    public ApiResponse<BookResponse> createBook(BookRequest request) {
        Book book = new Book();
        book.setTitle(request.getTitle());
        book.setDescription(request.getDescription());
        book.setPublishYear(request.getPublishYear());
        book.setQuantity(request.getQuantity());
        book.setCreatedAt(LocalDateTime.now());
        book.setUpdatedAt(LocalDateTime.now());
        book.setIsDeleted(false);
        
        // Set category
        if (request.getCategoryId() != null) {
            Category category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
            book.setCategory(category);
        }
        
        // Set authors
        if (request.getAuthorIds() != null && !request.getAuthorIds().isEmpty()) {
            List<Author> authors = authorRepository.findAllById(request.getAuthorIds());
            book.setAuthors(authors);
        }
        
        Book savedBook = bookRepository.save(book);
        
        return ApiResponse.<BookResponse>builder()
                .code(1000)
                .message("Tạo sách thành công")
                .result(bookMapper.toBookResponse(savedBook))
                .build();
    }
    
    public ApiResponse<BookResponse> updateBook(Long id, BookRequest request) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.BOOK_NOT_FOUND));
        
        if (book.getIsDeleted()) {
            throw new AppException(ErrorCode.BOOK_NOT_FOUND);
        }
        
        book.setTitle(request.getTitle());
        book.setDescription(request.getDescription());
        book.setPublishYear(request.getPublishYear());
        book.setQuantity(request.getQuantity());
        book.setUpdatedAt(LocalDateTime.now());
        
        // Update category
        if (request.getCategoryId() != null) {
            Category category = categoryRepository.findById(request.getCategoryId())
                    .orElseThrow(() -> new AppException(ErrorCode.CATEGORY_NOT_FOUND));
            book.setCategory(category);
        }
        
        // Update authors
        if (request.getAuthorIds() != null && !request.getAuthorIds().isEmpty()) {
            List<Author> authors = authorRepository.findAllById(request.getAuthorIds());
            book.setAuthors(authors);
        }
        
        Book updatedBook = bookRepository.save(book);
        
        return ApiResponse.<BookResponse>builder()
                .code(1000)
                .message("Cập nhật sách thành công")
                .result(bookMapper.toBookResponse(updatedBook))
                .build();
    }

    public ApiResponse<Void> deleteBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.BOOK_NOT_FOUND));
        
        book.setIsDeleted(true);
        book.setUpdatedAt(LocalDateTime.now());
        bookRepository.save(book);
        
        return ApiResponse.<Void>builder()
                .code(1000)
                .message("Xóa sách thành công")
                .build();
    }
    
    public ApiResponse<List<BookResponse>> searchBooks(String keyword) {
        List<BookResponse> books = bookRepository.findByTitleContainingAndIsDeletedFalse(keyword)
                .stream()
                .map(bookMapper::toBookResponse)
                .collect(Collectors.toList());
        
        return ApiResponse.<List<BookResponse>>builder()
                .code(1000)
                .message("Tìm thấy " + books.size() + " sách")
                .result(books)
                .build();
    }
    
    public ApiResponse<List<BookResponse>> getAvailableBooks() {
        List<BookResponse> books = bookRepository.findAvailableBooks()
                .stream()
                .map(bookMapper::toBookResponse)
                .collect(Collectors.toList());
        
        return ApiResponse.<List<BookResponse>>builder()
                .code(1000)
                .message("Lấy danh sách sách có sẵn thành công")
                .result(books)
                .build();
    }
}
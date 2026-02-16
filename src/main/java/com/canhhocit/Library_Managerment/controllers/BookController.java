package com.canhhocit.Library_Managerment.controllers;

import com.canhhocit.Library_Managerment.dto.request.BookRequest;
import com.canhhocit.Library_Managerment.dto.response.ApiResponse;
import com.canhhocit.Library_Managerment.dto.response.BookResponse;
import com.canhhocit.Library_Managerment.services.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {
    
    private final BookService bookService;

    @GetMapping
    public ApiResponse<List<BookResponse>> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public ApiResponse<BookResponse> getBookById(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    @PostMapping
    public ApiResponse<BookResponse> createBook(@RequestBody BookRequest request) {
        return bookService.createBook(request);
    }

    @PutMapping("/{id}")
    public ApiResponse<BookResponse> updateBook(
            @PathVariable Long id, 
            @RequestBody BookRequest request) {
        return bookService.updateBook(id, request);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteBook(@PathVariable Long id) {
        return bookService.deleteBook(id);
    }

    @GetMapping("/search")
    public ApiResponse<List<BookResponse>> searchBooks(@RequestParam String keyword) {
        return bookService.searchBooks(keyword);
    }

    @GetMapping("/available")
    public ApiResponse<List<BookResponse>> getAvailableBooks() {
        return bookService.getAvailableBooks();
    }
}
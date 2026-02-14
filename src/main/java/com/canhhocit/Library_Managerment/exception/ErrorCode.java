package com.canhhocit.Library_Managerment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.Getter;

@Getter
public enum ErrorCode {
    // General errors
    KEY_INVALID(1001, "Uncategorize error", HttpStatus.BAD_REQUEST),
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorize exception", HttpStatus.INTERNAL_SERVER_ERROR),
    
    // User errors (1002-1099)
    USER_EXISTED(1002, "User existed", HttpStatus.BAD_REQUEST),
    USER_NOT_EXISTED(1003, "User not existed", HttpStatus.NOT_FOUND),
    USERNAME_INVALID(1004, "Username must be at least {min} characters", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(1005, "Password must be at least {min} characters", HttpStatus.BAD_REQUEST),
    INVALID_DOB(1008, "Your age must be at least {min}", HttpStatus.BAD_REQUEST),
    
    // Authentication & Authorization (1006-1007)
    UNAUTHENTICATED(1006, "Unauthenticated", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED(1007, "You do not have permission", HttpStatus.FORBIDDEN),
    
    // Category errors (2001-2099)
    CATEGORY_NOT_FOUND(2001, "Category not found", HttpStatus.NOT_FOUND),
    CATEGORY_EXISTED(2002, "Category already existed", HttpStatus.BAD_REQUEST),
    CATEGORY_NAME_REQUIRED(2003, "Category name is required", HttpStatus.BAD_REQUEST),
    
    // Book errors (3001-3099)
    BOOK_NOT_FOUND(3001, "Book not found", HttpStatus.NOT_FOUND),
    BOOK_OUT_OF_STOCK(3002, "Book is out of stock", HttpStatus.BAD_REQUEST),
    BOOK_TITLE_REQUIRED(3003, "Book title is required", HttpStatus.BAD_REQUEST),
    
    // Author errors (4001-4099)
    AUTHOR_NOT_FOUND(4001, "Author not found", HttpStatus.NOT_FOUND),
    
    // Borrow errors (5001-5099)
    BORROW_NOT_FOUND(5001, "Borrow record not found", HttpStatus.NOT_FOUND),
    BORROW_ALREADY_RETURNED(5002, "Book already returned", HttpStatus.BAD_REQUEST);

    private int code;
    private String message;
    private HttpStatusCode statusCode;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }
}
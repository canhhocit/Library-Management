package com.canhhocit.Library_Managerment.controllers;

import com.canhhocit.Library_Managerment.dto.request.BorrowRequest;
import com.canhhocit.Library_Managerment.dto.response.ApiResponse;
import com.canhhocit.Library_Managerment.dto.response.BorrowResponse;
import com.canhhocit.Library_Managerment.services.BorrowService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/borrows")
@RequiredArgsConstructor
public class BorrowController {
    
    private final BorrowService borrowService;

    @GetMapping
    public ApiResponse<List<BorrowResponse>> getAllBorrows() {
        return borrowService.getAllBorrows();
    }

    @GetMapping("/{id}")
    public ApiResponse<BorrowResponse> getBorrowById(@PathVariable Long id) {
        return borrowService.getBorrowById(id);
    }

    @GetMapping("/user/{userId}")
    public ApiResponse<List<BorrowResponse>> getBorrowsByUserId(@PathVariable Long userId) {
        return borrowService.getBorrowsByUserId(userId);
    }

    @PostMapping
    public ApiResponse<BorrowResponse> createBorrow(@RequestBody BorrowRequest request) {
        return borrowService.createBorrow(request);
    }

    @PutMapping("/{id}/return")
    public ApiResponse<BorrowResponse> returnBorrow(@PathVariable Long id) {
        return borrowService.returnBorrow(id);
    }
}
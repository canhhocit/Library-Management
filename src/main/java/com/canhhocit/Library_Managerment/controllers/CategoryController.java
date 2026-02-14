package com.canhhocit.Library_Managerment.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.canhhocit.Library_Managerment.dto.request.CategoryRequest;
import com.canhhocit.Library_Managerment.dto.response.ApiResponse;
import com.canhhocit.Library_Managerment.dto.response.CategoryResponse;
import com.canhhocit.Library_Managerment.services.CategoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {
    
    private final CategoryService categoryService;

    // GET ALL
    @GetMapping
    public ApiResponse<List<CategoryResponse>> getAllCategories() {
        return categoryService.getAllCategory();
    }
    
    // GET ID
    @GetMapping("/{id}")
    public ApiResponse<CategoryResponse> getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryById(id);
    }
    
    @PostMapping
    public ApiResponse<CategoryResponse> createCategory(@RequestBody CategoryRequest request) {
        return categoryService.createCategory(request);
    }
    
    @PutMapping("/{id}")
    public ApiResponse<CategoryResponse> updateCategory(
            @PathVariable Long id, 
            @RequestBody CategoryRequest request) {
        return categoryService.updateCategory(id, request);
    }
    
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteCategory(@PathVariable Long id) {
        return categoryService.deleteCategory(id);
    }
}
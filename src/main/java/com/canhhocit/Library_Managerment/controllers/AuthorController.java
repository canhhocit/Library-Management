package com.canhhocit.Library_Managerment.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.canhhocit.Library_Managerment.dto.request.AuthorRequest;
import com.canhhocit.Library_Managerment.dto.response.ApiResponse;
import com.canhhocit.Library_Managerment.dto.response.AuthorResponse;
import com.canhhocit.Library_Managerment.services.AuthorService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/authors")
@RequiredArgsConstructor
public class AuthorController {
    private final AuthorService authorService;

    @GetMapping
    public ApiResponse<List<AuthorResponse>> getAuthors(
            @RequestParam(required = false) String name) {

        if (name == null || name.isBlank()) {
            return authorService.getAllAuthors();
        }

        return authorService.getAuthorByName(name);
    }

    @PostMapping
    public ApiResponse<AuthorResponse> createAuthor(@RequestBody AuthorRequest authorRequest) {
        return authorService.createAuthor(authorRequest);
    }

    @PutMapping("/{id}")
    public ApiResponse<AuthorResponse> updateAuthor(@PathVariable Long id, @RequestBody AuthorRequest authorRequest) {
        return authorService.updateAuthor(id, authorRequest);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteAuthor(@PathVariable Long id) {
        return authorService.deleteAuthor(id);
    }

}

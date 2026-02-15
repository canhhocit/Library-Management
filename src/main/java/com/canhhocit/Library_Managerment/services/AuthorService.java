package com.canhhocit.Library_Managerment.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.canhhocit.Library_Managerment.dto.request.AuthorRequest;
import com.canhhocit.Library_Managerment.dto.response.ApiResponse;
import com.canhhocit.Library_Managerment.dto.response.AuthorResponse;
import com.canhhocit.Library_Managerment.entities.Author;
import com.canhhocit.Library_Managerment.exception.AppException;
import com.canhhocit.Library_Managerment.exception.ErrorCode;
import com.canhhocit.Library_Managerment.mapper.AuthorMapper;
import com.canhhocit.Library_Managerment.repositories.AuthorRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthorService {
    AuthorRepository authorRepository;
    AuthorMapper authorMapper;

    //getAllAuthors
    public ApiResponse<List<AuthorResponse>> getAllAuthors() {
        List<AuthorResponse> authors = authorRepository.findAll().stream()
                .map(authorMapper::toAuthorResponse)
                .collect(Collectors.toList());
        return ApiResponse.<List<AuthorResponse>>builder()
                .code(1000)
                .message("Authors retrieved successfully")
                .result(authors)
                .build();
    }

    //get list ByName
    public ApiResponse<List<AuthorResponse>> getAuthorByName(String name) {
        List<AuthorResponse> authors = authorRepository.searchByName(name).stream()
                .map(authorMapper::toAuthorResponse)
                .collect(Collectors.toList());
        int total = authors.size();
        String message = total == 0 ? "Authors not found" : "Tìm thấy " + total + " tác giả trùng khớp";
        return ApiResponse.<List<AuthorResponse>>builder()
                .code(total == 0 ? 1001 : 1000)
                .message(message)
                .result(authors)
                .build();
    }

    //createAuthor
    public ApiResponse<AuthorResponse> createAuthor(AuthorRequest authorRequest) {
        Author author = authorMapper.toAuthor(authorRequest);
        authorRepository.save(author);
        return ApiResponse.<AuthorResponse>builder()
                .code(1000)
                .message("Author created successfully")
                .result(authorMapper.toAuthorResponse(author))
                .build();
    }

    //updateAuthor
    public ApiResponse<AuthorResponse> updateAuthor(Long id, AuthorRequest authorRequest) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.AUTHOR_NOT_FOUND));
        author.setName(authorRequest.getName());
        author.setBiography(authorRequest.getBiography());
        authorRepository.save(author);
        return ApiResponse.<AuthorResponse>builder()
                .code(1000)
                .message("Author updated successfully")
                .result(authorMapper.toAuthorResponse(author))
                .build();
    }

    //deleteAuthor
    public ApiResponse<Void> deleteAuthor(Long id) {
        Author author = authorRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.AUTHOR_NOT_FOUND));
        authorRepository.delete(author);
        return ApiResponse.<Void>builder()
                .code(1000)
                .message("Author deleted successfully")
                .build();
    }
}

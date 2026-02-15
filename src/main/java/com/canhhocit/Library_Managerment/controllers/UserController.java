package com.canhhocit.Library_Managerment.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import com.canhhocit.Library_Managerment.dto.request.UserCreateRequest;
import com.canhhocit.Library_Managerment.dto.request.UserUpdateRequest;
import com.canhhocit.Library_Managerment.dto.response.ApiResponse;
import com.canhhocit.Library_Managerment.dto.response.UserResponse;
import com.canhhocit.Library_Managerment.services.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ApiResponse<UserResponse> createUser(
            @RequestBody UserCreateRequest request) {
        return userService.createUser(request);
    }

    // Get all
    @GetMapping
    public ApiResponse<List<UserResponse>> getAllUsers() {
        return userService.getAllUsers();
    }

    // Update
    @PutMapping("/{username}")
    public ApiResponse<UserResponse> updateUser(
            @PathVariable String username,
            @RequestBody UserUpdateRequest request) {
        return userService.updateUser(username, request);
    }

    @DeleteMapping("/{username}")
    public ApiResponse<Void> deleteUser(
            @PathVariable String username) {
        return userService.deleteUser(username);
    }

    // Search by full name
    @GetMapping("/search")
    public ApiResponse<List<UserResponse>> searchUserByFullName(
            @RequestParam String fullName) {
        return userService.getUserByFullName(fullName);
    }

    // Search by username
    @GetMapping("/{username}")
    public ApiResponse<UserResponse> searchUserByUsername(@PathVariable String username) {
        return userService.getUserbyUsername(username);
    }

}

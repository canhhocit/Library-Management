package com.canhhocit.Library_Managerment.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.canhhocit.Library_Managerment.dto.request.UserCreateRequest;
import com.canhhocit.Library_Managerment.dto.request.UserUpdateRequest;
import com.canhhocit.Library_Managerment.dto.response.ApiResponse;
import com.canhhocit.Library_Managerment.dto.response.UserResponse;
import com.canhhocit.Library_Managerment.entities.User;
import com.canhhocit.Library_Managerment.exception.AppException;
import com.canhhocit.Library_Managerment.exception.ErrorCode;
import com.canhhocit.Library_Managerment.mapper.UserMapper;
import com.canhhocit.Library_Managerment.repositories.UserRepository;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService {
    UserRepository userRepository;
    UserMapper userMapper;

    // create
    public ApiResponse<UserResponse> createUser(UserCreateRequest request) {
        User user = userMapper.toUserCreate(request);
        user.setRole("USER");
        user.setStatus("ACTIVE");
        user.setCreatedAt(LocalDateTime.now());
        log.info("USER: Create new user");
        return ApiResponse.<UserResponse>builder()
                .code(1000)
                .message("Tạo tài khoản thành công")
                .result(userMapper.toUserResponse(userRepository.save(user))).build();
    }

    // getAll
    public ApiResponse<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = userRepository.findAll()
                .stream().map(userMapper::toUserResponse).collect(Collectors.toList());

        return ApiResponse.<List<UserResponse>>builder()
                .code(1000)
                .message("Danh sách tài khoản người dùng")
                .result(users)
                .build();
    }

    // get by username
    public ApiResponse<UserResponse> getUserbyUsername(String username) {
        User user = userRepository.findByUsername(username.trim())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        return ApiResponse.<UserResponse>builder()
                .code(1000)
                .message("Tìm thấy tài khoản có tên đăng nhập: " + username)
                .result(userMapper.toUserResponse(user))
                .build();
    }

    // get by Fullname
    public ApiResponse<List<UserResponse>> getUserByFullName(String fullName) {
        String keyword = fullName == null ? "" : fullName.trim();

        List<UserResponse> users = userRepository.searchByFullName(keyword)
                .stream().map(userMapper::toUserResponse).toList();
        int total = users.size();
        String message = (total > 0)
                ? "Tìm thấy " + total + " tài khoản có tên: " + keyword
                : "Không tìm thấy tài khoản nào có tên: " + keyword;

        return ApiResponse.<List<UserResponse>>builder()
                .code(1000)
                .message(message)
                .result(users)
                .build();
    }

    public ApiResponse<UserResponse> updateUser(String username, UserUpdateRequest request) {
        User oldUser = userRepository.findByUsername(username.trim())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        userMapper.toUserUpdate(oldUser,request);
        return ApiResponse.<UserResponse>builder()
                .code(1000)
                .message("Cập nhật tài khoản thành công")
                .result(userMapper.toUserResponse(userRepository.save(oldUser))).build();
    }

    public ApiResponse<Void> deleteUser(String username) {
        User user = userRepository.findByUsername(username.trim())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        userRepository.delete(user);
        return ApiResponse.<Void>builder()
                .code(1000)
                .message("Xóa tài khoản thành công")
                .build();
    }

}

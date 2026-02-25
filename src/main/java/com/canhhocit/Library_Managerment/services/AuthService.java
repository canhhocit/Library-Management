package com.canhhocit.Library_Managerment.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.canhhocit.Library_Managerment.config.JwtUtil;
import com.canhhocit.Library_Managerment.dto.request.LoginRequest;
import com.canhhocit.Library_Managerment.dto.response.ApiResponse;
import com.canhhocit.Library_Managerment.dto.response.LoginResponse;
import com.canhhocit.Library_Managerment.entities.User;
import com.canhhocit.Library_Managerment.exception.AppException;
import com.canhhocit.Library_Managerment.exception.ErrorCode;
import com.canhhocit.Library_Managerment.repositories.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public ApiResponse<LoginResponse> login(LoginRequest request) {
        // 1. Tìm user theo username
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        // 2. Kiểm tra password
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }

        // 3. Tạo JWT token
        String token = jwtUtil.generateToken(user);

        return ApiResponse.<LoginResponse>builder()
                .code(1000)
                .message("Đăng nhập thành công")
                .result(new LoginResponse(token, user.getUsername(), user.getRole()))
                .build();
    }
}
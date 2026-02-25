package com.canhhocit.Library_Managerment.config;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import com.canhhocit.Library_Managerment.entities.User;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtUtil {

    private final JwtEncoder jwtEncoder;

    public String generateToken(User user) {
        Instant now = Instant.now();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("library-webapp")           // ai phát hành token
                .issuedAt(now)                   // thời điểm tạo
                .expiresAt(now.plus(8, ChronoUnit.HOURS)) // hết hạn sau 8 tiếng
                .subject(user.getUsername())     // username trong token
                .claim("role", user.getRole())   // thêm role vào token
                .claim("userId", user.getId())   // thêm userId vào token
                .build();

        return jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}
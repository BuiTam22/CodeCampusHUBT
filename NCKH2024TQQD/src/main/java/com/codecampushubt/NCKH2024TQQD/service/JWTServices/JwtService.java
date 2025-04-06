package com.codecampushubt.NCKH2024TQQD.service.JWTServices; // Package chứa service xử lý JWT

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtService {

    @Value("${jwt.secret}") // Lấy secret key từ file cấu hình
    private String SECRET_KEY;

    // Phương thức tạo token từ username
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username) // Lưu username vào payload
                .setIssuedAt(new Date()) // Ngày phát hành token
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // Token hết hạn sau 1 ngày
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY) // Ký token bằng thuật toán HS256
                .compact(); // Tạo token dạng string
    }

    // Giải mã token và lấy claims
    private Claims extractClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY) // Dùng secret key để giải mã
                .parseClaimsJws(token) // Giải mã token
                .getBody(); // Lấy payload (claims)
    }

    // Phương thức lấy username từ token
    public String extractUsername(String token) {
        return extractClaims(token).getSubject(); // Lấy subject (username) từ claims
    }

    // Phương thức kiểm tra token có hợp lệ không
    public boolean validateToken(String token) {
        try {
            Claims claims = extractClaims(token); // Giải mã token
            return claims.getExpiration().after(new Date()); // Kiểm tra token còn hạn không
        } catch (Exception e) {
            return false; // Nếu có lỗi thì token không hợp lệ
        }
    }
}

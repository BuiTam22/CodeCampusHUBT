package com.codecampushubt.NCKH2024TQQD.security; // Package chứa class filter kiểm tra JWT

import com.codecampushubt.NCKH2024TQQD.service.JWTServices.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component // Đánh dấu đây là một bean quản lý bởi Spring
public class JwtFilter extends OncePerRequestFilter { // Lớp filter chạy một lần duy nhất mỗi request

    private final JwtService jwtService;

    @Autowired
    public JwtFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String token = request.getHeader("Authorization"); // Lấy token từ header Authorization

        if (token != null && token.startsWith("Bearer ")) { // Kiểm tra token có định dạng hợp lệ không
            token = token.substring(7); // Loại bỏ "Bearer " để lấy token thực sự
            if (jwtService.validateToken(token)) { // Kiểm tra token hợp lệ không
                String username = jwtService.extractUsername(token); // Lấy username từ token
                request.setAttribute("username", username); // Đặt username vào request để sử dụng trong controller
            }
        }
        chain.doFilter(request, response); // Tiếp tục chuỗi filter
    }
}

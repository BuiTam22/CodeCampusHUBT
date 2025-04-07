package com.codecampushubt.NCKH2024TQQD.security;

import java.io.IOException;
import java.util.List;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.codecampushubt.NCKH2024TQQD.service.JWTServices.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Autowired
    public JwtFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {

        String token = null;
        String requestPath = request.getRequestURI();

        // Kiểm tra các đường dẫn được cho phép không cần xác thực
        if (isPublicPath(requestPath)) {
            chain.doFilter(request, response);
            return;
        }

        // Lấy token từ cookie
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("token".equals(cookie.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }
        }

        if (token != null && jwtService.validateToken(token)) {
            String username = jwtService.extractUsername(token);
            List<String> permissions = jwtService.extractPermissions(token);

            if (username != null) {
                // Kiểm tra xem người dùng có quyền truy cập vào đường dẫn hiện tại không
                if (hasPermission(permissions, requestPath)) {
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    username, null, Collections.emptyList());

                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    chain.doFilter(request, response);
                } else {
                    // Người dùng không có quyền truy cập
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN); // 403 Forbidden
                    response.getWriter().write("Access denied: You don't have permission to access this resource");
                }
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 Unauthorized
                response.getWriter().write("Invalid token");
            }
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 Unauthorized
            response.getWriter().write("Authentication required");
        }
    }

    private boolean isPublicPath(String path) {
        return path.equals("/api/user/login") ||
                path.equals("/login/show") ||
                path.startsWith("/AdminStatic/") ||
                path.startsWith("/ClientStatic/");
    }

    private boolean hasPermission(List<String> permissions, String requestPath) {
        if (permissions == null || permissions.isEmpty()) {
            return false;
        }

        // Kiểm tra xem requestPath có trong danh sách permissions không
        for (String permittedPath : permissions) {
            // Xử lý các trường hợp có path params như "/api/user/basic-info/{id}"
            if (permittedPath.contains("{") && permittedPath.contains("}")) {
                // Chuyển đổi permittedPath thành regex pattern
                String pattern = permittedPath.replaceAll("\\{[^}]+\\}", "[^/]+");
                pattern = "^" + pattern + "$";
                if (requestPath.matches(pattern)) {
                    return true;
                }
            } else if (permittedPath.equals(requestPath)) {
                return true;
            }
        }

        return false;
    }
}
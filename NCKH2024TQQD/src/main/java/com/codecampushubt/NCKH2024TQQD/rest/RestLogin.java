package com.codecampushubt.NCKH2024TQQD.rest;

import com.codecampushubt.NCKH2024TQQD.dto.LoginDTO.LoginBasicDTO;
import com.codecampushubt.NCKH2024TQQD.dto.LoginDTO.LoginRequestDTO;
import com.codecampushubt.NCKH2024TQQD.dto.LoginDTO.LoginResponseDTO;
import com.codecampushubt.NCKH2024TQQD.service.JWTServices.JwtService;
import com.codecampushubt.NCKH2024TQQD.util.BCryptPasswordUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.codecampushubt.NCKH2024TQQD.service.UserServices.UserService;

@RestController
@RequestMapping("/api/user")
public class RestLogin {

    private final UserService userService;
    private final JwtService jwtService;
    private final BCryptPasswordUtil bCryptPasswordUtil;

    @Autowired
    public RestLogin(UserService userService, JwtService jwtService, BCryptPasswordUtil bCryptPasswordUtil1){
        this.userService = userService;
        this.jwtService = jwtService;
        this.bCryptPasswordUtil = bCryptPasswordUtil1;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO request,    HttpServletResponse response) {
        LoginBasicDTO user = userService.getLoginBasicDTO(request.getUsername());

        if (user == null || !bCryptPasswordUtil.passwordMatches(request.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        String token = jwtService.generateToken(user.getUserName());

        // Tạo cookie chứa JWT
        ResponseCookie cookie = ResponseCookie.from("token", token)
                .httpOnly(true)
                .secure(false) // Đặt true nếu dùng HTTPS
                .path("/")
                .maxAge(24 * 60 * 60) // 1 ngày
                .build();

        response.setHeader("Set-Cookie", cookie.toString());

        return ResponseEntity.ok("Login successful");
    }

}

package com.codecampushubt.NCKH2024TQQD.rest;

import com.codecampushubt.NCKH2024TQQD.dto.LoginDTO.LoginBasicDTO;
import com.codecampushubt.NCKH2024TQQD.dto.LoginDTO.LoginRequestDTO;
import com.codecampushubt.NCKH2024TQQD.dto.LoginDTO.LoginResponseDTO;
import com.codecampushubt.NCKH2024TQQD.service.JWTServices.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.codecampushubt.NCKH2024TQQD.service.UserServices.UserService;

@RestController
@RequestMapping("/api/user")
public class RestLogin {
    private final UserService userService;
    private final JwtService jwtService;
    
    @Autowired
    public RestLogin(UserService userService, JwtService jwtService){
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO request) {
        // lấy thông tin để đăng nhập cơ bản, có thêm trường "email" so vơi LoginRequestDTO
        LoginBasicDTO user = userService.getLoginBasicDTO(request.getUsername());

        if (user == null || !passwordMatches(request.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

        String token = jwtService.generateToken(user.getUserName());
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    // hàm check so sánh 2 mật khẩu được mã hóa dưới dạng BCrypt
    private boolean passwordMatches(String rawPassword, String hashedPassword) {
        return new BCryptPasswordEncoder().matches(rawPassword, hashedPassword);
    }
    
}

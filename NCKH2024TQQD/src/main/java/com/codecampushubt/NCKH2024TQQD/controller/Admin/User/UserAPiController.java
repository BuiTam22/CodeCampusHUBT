package com.codecampushubt.NCKH2024TQQD.controller.Admin.User;

import com.codecampushubt.NCKH2024TQQD.dao.UserRepository;
import com.codecampushubt.NCKH2024TQQD.dto.UserDTO.UserCreateDTO;
import com.codecampushubt.NCKH2024TQQD.dto.UserDTO.UserShowDTO;
import com.codecampushubt.NCKH2024TQQD.dto.UserDTO.UserUpdateDTO;
import com.codecampushubt.NCKH2024TQQD.entity.User;
import com.codecampushubt.NCKH2024TQQD.service.UserServices.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/api/user")
public class UserAPiController {
    private final UserService userService;


    public UserAPiController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/show")
    public ResponseEntity<List<UserShowDTO>> getAllUsers() {
        List<UserShowDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);

    }

    @PostMapping("/add")
    public ResponseEntity<?> createUser(@RequestBody UserCreateDTO dto) {
//        System.out.println(user);
        try {
            userService.addUser(dto); // Gọi phương thức thêm người dùng từ UserService
            System.out.println(dto);
            return ResponseEntity.ok("Thêm người dùng thành công!");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage()); // Trả lỗi nếu có vấn đề
        }
    }
    @PostMapping("/update/{userID}")
    public ResponseEntity<?> updateUser(@ModelAttribute UserUpdateDTO dto, @PathVariable long userID) {
        System.out.println("đã vào controller");
        System.out.println(userID);
        System.out.println(dto);
        userService.updateUser(userID, dto);
        return ResponseEntity.ok("Cập Nhật Thành Công ");
    }

}

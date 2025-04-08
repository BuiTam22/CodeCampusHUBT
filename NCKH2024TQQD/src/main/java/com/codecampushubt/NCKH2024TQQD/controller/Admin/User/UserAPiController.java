package com.codecampushubt.NCKH2024TQQD.controller.Admin.User;

import com.codecampushubt.NCKH2024TQQD.dto.UserDTO.UserShowDTO;
import com.codecampushubt.NCKH2024TQQD.service.UserServices.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("admin/api/user")
public class UserAPiController {
    private final UserService userService;
    public UserAPiController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/show")
    public List<UserShowDTO> getAllUsers() {
        return userService.getAllUserShowDTO();

    }
}

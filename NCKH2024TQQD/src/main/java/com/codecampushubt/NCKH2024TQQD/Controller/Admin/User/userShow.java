package com.codecampushubt.NCKH2024TQQD.Controller.Admin.User;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin/user")
public class userShow {
    @GetMapping("/show")
    public String showUser(){
        return "Admin/user/show";
    }
}

package com.codecampushubt.NCKH2024TQQD.Controller.Admin.Role;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin/role")
public class roleShow {
    @GetMapping("/show")
    public String showRole(){
        return "Admin/Role/show";
    }
}

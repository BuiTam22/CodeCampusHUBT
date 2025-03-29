package com.codecampushubt.NCKH2024TQQD.Controller.Admin.Dashboard;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin/dashboard")
public class dashboardShow {
    @GetMapping("/show")
    public String showDashboard(){
        return "Admin/dashboard/show";
    }
}

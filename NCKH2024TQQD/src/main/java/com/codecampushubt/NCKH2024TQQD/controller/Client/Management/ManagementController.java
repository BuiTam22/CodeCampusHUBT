package com.codecampushubt.NCKH2024TQQD.controller.Client.Management;

import com.codecampushubt.NCKH2024TQQD.dto.CourseDTO.CourseShowDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/management")
public class ManagementController {
    @GetMapping("")
    public String showCourse(Model model, HttpServletRequest request){

        model.addAttribute("activePage", request.getRequestURI());
        return "ClientTemplates/management/management";
    }
}

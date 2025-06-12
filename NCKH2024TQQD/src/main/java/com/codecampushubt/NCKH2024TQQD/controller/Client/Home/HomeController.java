package com.codecampushubt.NCKH2024TQQD.controller.Client.Home;

import com.codecampushubt.NCKH2024TQQD.dto.CourseDTO.CourseShowDTO;
import com.codecampushubt.NCKH2024TQQD.service.CourseServices.CourseService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("")
public class HomeController {
    private final CourseService courseService;

    public HomeController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public String showHome(Model model, HttpServletRequest request){
        List<CourseShowDTO> courses = courseService.getCourseShowDTO();
        model.addAttribute("courses", courses);
        model.addAttribute("activePage", request.getRequestURI());
        return "ClientTemplates/home/index";
    }
}

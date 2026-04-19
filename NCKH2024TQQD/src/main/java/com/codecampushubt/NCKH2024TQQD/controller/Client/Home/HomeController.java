package com.codecampushubt.NCKH2024TQQD.controller.Client.Home;

import com.codecampushubt.NCKH2024TQQD.dto.CourseDTO.CourseShowDTO;
import com.codecampushubt.NCKH2024TQQD.dto.LessonDTO.HomeLessonDTO;
import com.codecampushubt.NCKH2024TQQD.service.CourseServices.CourseService;
import com.codecampushubt.NCKH2024TQQD.service.LessonServices.LessonService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("")
public class HomeController {
    private final CourseService courseService;
    private final LessonService lessonService;

    public HomeController(CourseService courseService, LessonService lessonService) {
        this.courseService = courseService;
        this.lessonService = lessonService;
    }

    @GetMapping
    public String showHome(Model model, HttpServletRequest request){
        List<CourseShowDTO> courses = courseService.getCourseShowDTO();
        List<HomeLessonDTO> topLessons = lessonService.getTopLessonsForHome(6);
        model.addAttribute("courses", courses);
        model.addAttribute("topLessons", topLessons);
        model.addAttribute("activePage", request.getRequestURI());
        return "ClientTemplates/home/index";
    }
}


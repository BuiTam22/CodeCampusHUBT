package com.codecampushubt.NCKH2024TQQD.controller.Client.Course;

import com.codecampushubt.NCKH2024TQQD.dto.CourseDTO.CourseModuleDTO;
import com.codecampushubt.NCKH2024TQQD.dto.CourseDTO.CourseShowDTO;
import com.codecampushubt.NCKH2024TQQD.entity.Course;
import com.codecampushubt.NCKH2024TQQD.service.CourseServices.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/course")
public class CourseController {

    private final CourseService courseService;

    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }


    @GetMapping("/show")
    public String showCourse(Model model){
        List<CourseShowDTO> courses = courseService.getCourseShowDTO();
        model.addAttribute("courses", courses);
        return "ClientTemplates/course/show";
    }

    @GetMapping("/show/{slug}")
    public String showDetailCourse(@PathVariable("slug") String theSlug,Model model){
        List<CourseModuleDTO> courseModules = courseService.getCourseModuleByCourseSlug(theSlug);
        model.addAttribute("courseModules", courseModules);
        return "ClientTemplates/course/course-module";
    }

}

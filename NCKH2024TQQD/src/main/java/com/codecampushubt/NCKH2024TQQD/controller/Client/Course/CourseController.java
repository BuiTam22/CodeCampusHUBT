package com.codecampushubt.NCKH2024TQQD.controller.Client.Course;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/course")
public class CourseController {

    @GetMapping("/show")
    public String showCourse(){
        return "ClientTemplates/course/show";
    }

}

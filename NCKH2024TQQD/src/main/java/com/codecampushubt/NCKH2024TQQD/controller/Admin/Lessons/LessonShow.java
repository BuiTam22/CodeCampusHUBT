package com.codecampushubt.NCKH2024TQQD.controller.Admin.Lessons;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/lesson")
public class LessonShow {
    @GetMapping("/show")
    public String show() {
        return "AdminTemplates/Lessons/show";
    }
}
